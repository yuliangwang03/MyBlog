package com.wyl.blog.service;

import com.wyl.blog.dao.CommentRepository;
import com.wyl.blog.entity.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author russe
 */
@Service
public class CommentServiceImpl implements  CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        /**按照评论建立的时间排序，将评论父级评论列出*/
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, Sort.by(Sort.Direction.DESC, "createTime"));
        return eachComment(comments);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment saveComment(Comment comment) {
        /**获取被评论文章的id*/
        Long parentCommentId = comment.getParentComment().getId();
        /**获取父级评论id，并且存入自己的参数中*/
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * iterate through each parent comment
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        /**为了防止数据库中的comment数据被修改，将数据copy到一个临时的list中进行操作*/
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        /**combine all child comments to parent comment group*/
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replies = comment.getReplyComments();
            for(Comment reply : replies) {
                /**find and save child comments in replies*/
                recursively(reply);
            }
            /**修改顶级节点的reply集合为迭代处理后的集合*/
            comment.setReplyComments(replies);
            /**清除临时存放区*/
            replies = new ArrayList<>();
        }
    }

    // get collections of all child nodes
    private List<Comment> tempReplies = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        tempReplies.add(comment);    //tempReplies is a collection of all parent nodes
        /**找到将所有的reply，并且平级的存入到父类评论中
         * 不断回溯找到父级评论下的所有replies*/
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replies = comment.getReplyComments();
            for (Comment reply : replies) {
                recursively(reply);
            }
        }
    }
}
