# Spring Boot Cache example

NOTE: __Must__ install lombok.jar so, the Eclipse won't complain that it cannot find the annotation @Slf4j

* Read and Cache

```
    @Cacheable(value = "post-single")
    public Post getPost(int id) {
        return repo.getPostData(id);
    }
```

* Add and Cache

```
    @CachePut(value="post-single", key="#post.id")
    public void addPost(Post post) {
        repo.addPostData( post.getId(), post);
    }
```

* Remove from Cache

```
    @CacheEvict(value="post-single")
    public void deletePost(int id)
    {
        dataMap.remove(new Integer(id));
    }
```

* Cache name must be specified in application.yml
The property name 'cache-names' can limit the name spaces for Cache. If this property doesn't exist, it will allow all. If exist, only the listed names will be allowed to cache

```
spring:
  cache:
    type: simple
    cache-names:
    - post-single

```


### References 
[Spring Boot cache with Redis](https://medium.com/@MatthewFTech/spring-boot-cache-with-redis-56026f7da83a)
[Spring IO - Cache](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html#boot-features-caching)
[Spring Cache Tutorial](http://www.baeldung.com/spring-cache-tutorial)