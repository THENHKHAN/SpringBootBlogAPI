package com.springboot.blogApp.service.impl;

import com.springboot.blogApp.customExceptions.ResourceNotFoundException;
import com.springboot.blogApp.models.Post;
import com.springboot.blogApp.payload.PostDto;
import com.springboot.blogApp.payload.PostResponse;
import com.springboot.blogApp.respository.PostRepository;
import com.springboot.blogApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired //  automatically create repo variable/object to call the jpaRepo methods
    private PostRepository postRepository; // so that we can  JpaRepository methods to communicate with DB

//    public PostServiceImpl(PostRepository postRepository){ // constructor based dependency : Since used above @Autowired so don't need to write Constructor
//        this.postRepository = postRepository;
//    }
    @Autowired
    private ModelMapper mapper; // 86. Map Post Entity to Post DTO using ModelMapper

    // Create Post in the DB
    @Override
    public PostDto createPost(PostDto postDto) {
//      for saving data to DB:   Convert DTO to Entity
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

//        Post newPost =  postRepository.save(post); // saving to DB
        Post post = mapToEntity(postDto); // created this mapToEntity function to reuse whenever we needed(it will do the task as above 4 line did)
        Post newPost =  postRepository.save(post);
//        System.out.println("What return after saving Data to DB : " + newPost); // It return the object which got inserted

//      for sending back Response:   convert Entity to PostDTO (Optional) since we send a message like inserted successfully or just the title
//        PostDto postResponse = new PostDto();
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setDescription(newPost.getDescription());
//        postResponse.setContent(newPost.getContent());
//        return postResponse;
        PostDto postResponse = mapToDto(newPost); // created this mapToDto function to reuse whenever we needed(it will do the task as above 4 line did)
        return postResponse;
    }

    // will get ALL the records(posts) as a list of Post
    @Override
    public List<PostDto> getAllPosts() {
       List<Post> posts = postRepository.findAll(); // since we get list of Post(Entity) and not PostDto (since it's  a payload we design to transfer)
//      so lets convert into List<PostDto>
//       List<PostDto>  listOfPostDto  = posts.stream().map( post-> mapToDto(post)).collect(Collectors.toList()); // below with comment to understand each Sequence
        List<PostDto> listOfPostDto = posts.stream() // Create a stream from the list of Post objects
                .map(post -> mapToDto(post)) // Convert each Post object to a PostDto object using the mapToDto method
                .collect(Collectors.toList()); // Collect the results into a new list

       return listOfPostDto;
    }

//    will get SINGLE POST based on the given id. But if id does not exist in the DB then it will throw a Custom Exception from customException Package of ResourceNotFoundException class.
    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Post", "id", id));
        PostDto postResponse = mapToDto(post);

        return postResponse;
    }

//    will UPDATE only post whose id is matches with post or record
    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        PostDto postResponse = mapToDto(updatedPost);

        return postResponse;
    }

//    will DELETE Only post whose id is matches with any post in the DB else throw custom exception.
    @Override
    public String deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post); // delete(post); method typically does not return anything. This method is part of the Spring Data JPA CrudRepository interface, and its purpose is to delete the entity passed to it from the database.
        return String.format("Post of id : %d  is deleted Successfully", id);
    }

// will get Number of posts based on the PageSize and PageNum
    public PostResponse getAllPostByPagination(int pageNo, int pageSize, String postsSortBy, String sortDir) {
        Sort sorted = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(postsSortBy).ascending() : Sort.by(postsSortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sorted);  // by default unsorted - EX : PageRequest.of(0, 3, Sort.by("name"));                     // http://localhost:8080/api/posts/pagination/?pageNo=1&pageSize=2
//        System.out.println(String.format("Pageable Object page : %s", pageable)); //Pageable Object page : Page request [number: 1, size 2, sort: UNSORTED] . number is the pageNum provide and size pageSize
        Page<Post> posts = postRepository.findAll(pageable);// by default Unsorted. // based on the pageable it will return only pageSize (number of records at once )records and from where?(will be defined by pageNum)
        List<Post> listOfPosts = posts.getContent();
//        System.out.println(String.format("list of posts Object after findAll method : %s", listOfPosts)); // list of posts Object after findAll method : [Post(id=2, title=Python Flask, description=First post for flask learning, content=In this blog post we'll be learning about how to create routes in Flask.), Post(id=1, title=java Spring Boot Updated Post Java 17, description=First Updated post for java Spring Boot , content=In  this blog post we'll be learning Java Spring Boot from JavaGuide Updated.), Post(id=7, title=Blog post 1 Updated, description=This is 1st blog post Updated , content=This is updated content .), Post(id=9, title=Blog post 2, description=This is 2nd blog post, content=This is content .), Post(id=10, title=Blog post 3, description=This is 3rd blog post, content=This is content of 3rd post.), Post(id=12, title=Blog post 4, description=This is 4 blog post, content=This is content of 4 post.)]
        List<PostDto> content = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
//        List<PostDto> content = posts.stream().map(post -> mapToDto(post)).toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNum(posts.getNumber()); // under Page interface it has methods to get the pageNum from Page
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements()); // similar to length attribute
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

    }





    //    since will always need to convert Entity to DTO. So lets keep it in one function so that we can use it whenever we need it.
    private PostDto mapToDto (Post post){

//       Convert Java Object to DTO
//        PostDto postResponse = new PostDto();
//        postResponse.setId(post.getId());
//        postResponse.setTitle(post.getTitle());
//        postResponse.setDescription(post.getDescription());
//        postResponse.setContent(post.getContent());

//        will use mapper APi
        PostDto postResponse = mapper.map(post, PostDto.class); // (source, destination)

        return postResponse;
    }

//    DTO to Entity
    private Post mapToEntity (PostDto postDto){
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        Post post = mapper.map(postDto, Post.class);

        return post;
    }

}
