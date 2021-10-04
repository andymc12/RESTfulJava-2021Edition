package net.andymc12.restfuljava.content;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;



public class YamlHttpMessageConverter implements HttpMessageConverter<Object> {
    private static final MediaType YAML_MEDIA_TYPE = MediaType.parseMediaTypes("application/yaml").get(0);

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return YAML_MEDIA_TYPE.isCompatibleWith(mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return YAML_MEDIA_TYPE.isCompatibleWith(mediaType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(YAML_MEDIA_TYPE);
    }

    @Override
    public Object read(Class<? extends Object> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        Yaml yaml = new Yaml();
        return yaml.load(new InputStreamReader(inputMessage.getBody()));
    }

    @Override
    public void write(Object obj, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        yaml.dump(obj, new OutputStreamWriter(outputMessage.getBody()));
    }
}