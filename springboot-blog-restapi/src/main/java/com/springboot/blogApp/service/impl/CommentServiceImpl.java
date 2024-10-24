package com.springboot.blogApp.service.impl;

import com.springboot.blogApp.customExceptions.BlogAPIException;
import com.springboot.blogApp.customExceptions.ResourceNotFoundException;
import com.springboot.blogApp.models.Comment;
import com.springboot.blogApp.models.Post;
import com.springboot.blogApp.payload.CommentDto;
import com.springboot.blogApp.respository.CommentRepository;
import com.springboot.blogApp.respository.PostRepository;
import com.springboot.blogApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
   private CommentRepository commentRepository;

    @Autowired
   private PostRepository postRepository ;

    @Autowired
   private ModelMapper mapper; // 87. Map Comment Entity to Comment DTO using ModelMapper




    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

//        retrieve posts entity by id
        Post post = postRepository.findById(postId).orElseThrow( () -> new ResourceNotFoundException("Post", "id", postId));
//        set post to comment entity
        comment.setPost(post);
        // save comment to DB
        Comment newComment = commentRepository.save(comment);
        CommentDto commentResponse = mapToDto(newComment);
        return commentResponse;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) { // get list of all comments of a provided postid
//        retrieve comment by postId
        List<Comment> comments = commentRepository.findByPostId(postId);
//        convert list of comments to list of comment DTO
        List<CommentDto> listOfCommentDtos = comments.stream()
                                            .map(comment -> mapToDto(comment))
                                            .toList();
        return listOfCommentDtos;
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

//      retrieve post by postId 1st from db and if post id not exist then throw exception
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
//        retrieve comment by id and if not exist then throw error
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
//         now go(In Exception package:write BlogAPIException ) and create a custom exception logic (business logic Whether a comment belong to a particular Post or not)

        if (comment.getPost().getId().equals(post.getId())){ // comment.getPost().getId() this is getting from Comment entity and post.getId() from Post entity and matching.
//          if comment.getPost().getId() and post.getId() become equal then condition true
            CommentDto commentDto = mapToDto(comment);
            return commentDto;
        }
        else{
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, String.format("Comment id : %d does not belong to any post", comment.getId()));
        }

    }

    // update comment by comment id if it belongs to  post id with commentId=postId
    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDtoRequest) {
        Post post  = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
        if(comment.getPost().getId().equals(post.getId())){
            comment.setName(commentDtoRequest.getName());
            comment.setEmail(commentDtoRequest.getEmail());
            comment.setBody(commentDtoRequest.getBody());
        Comment updatedComment = commentRepository.save(comment); // saving to DB
//            convert Entity to DTO
        CommentDto commentDtoResponse = mapToDto(updatedComment);
            return commentDtoResponse;
        }
        else {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, String.format("Comment id : %d does not belong to any post", comment.getId()));
        }
    }

//    DELETE comment by comment id if it belongs to  post id with commentId=postId
   @Override
    public String deleteComment (Long postId, Long commentId){
       Post post  = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
       Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id" , commentId));

       if(comment.getPost().getId().equals(post.getId())){
           commentRepository.delete(comment);

          return String.format("Comment of id : %d  which belong to Post id : %d is deleted Successfully", commentId, postId);
       }
       else {
           throw  new BlogAPIException(HttpStatus.BAD_REQUEST, String.format("Comment id : %d does not belong to any post", comment.getId()));
       }
    }


    //    this will map from DTO to Entity
    private Comment mapToEntity(CommentDto commentDto) {
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        // will be using modelmapper API : 87. Map Comment Entity to Comment DTO using ModelMapper

        Comment comment = mapper.map(commentDto, Comment.class); // (source, destination)

        return comment;
    }

    private CommentDto mapToDto(Comment comment) {
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        // will be using modelmapper API : 87. Map Comment Entity to Comment DTO using ModelMapper
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }



}
