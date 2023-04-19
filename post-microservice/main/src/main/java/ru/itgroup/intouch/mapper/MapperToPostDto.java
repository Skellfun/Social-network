package ru.itgroup.intouch.mapper;
import lombok.AllArgsConstructor;
import model.post.Post;
import model.post.PostTag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.PostDto;
import java.util.Collection;

@Component
@AllArgsConstructor
public class MapperToPostDto {

    private final ModelMapper modelMapper;
    private final PropertyManager propertyManager;
    public PostDto getPostDto(Post postEntity) {
        TypeMap<Post, PostDto> propertyMapper = propertyManager.getPostPropertyMapper();
        Converter<Collection<PostTag>, Collection<String>> collectionToTagString =
                c -> c.getSource().stream().map(PostTag::getName).toList();
        propertyMapper.addMappings(mapper -> mapper.map(src -> src.getType().getCode(), PostDto::setType));
        propertyMapper.addMappings(mapper -> mapper.using(collectionToTagString).map(Post::getTags, PostDto::setTags));
        return this.modelMapper.map(postEntity, PostDto.class);
    }

}
