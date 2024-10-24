package com.springboot.blogApp.payload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// will use this for making more meaningful specially related to pagination so will keep only those attribute which are important to send back to client
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> content; // desired rec based on PNum&Psize
    private int pageNum ; // Provided in the queryParams OR default
    private int pageSize ; // Provided in the queryParams OR default
    private long totalElements; // total number of rec present in the Table
    private int totalPages; // Total pages ( Its calculated based on the default values of PNum and Psize that's y default value is useful in pagination
    private boolean last; // it returns true/false based on the pageNum
}
