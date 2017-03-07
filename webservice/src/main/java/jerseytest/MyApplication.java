package jerseytest;

import java.util.HashSet;
import java.util.Set;
 






import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.multipart.impl.FormDataMultiPartDispatchProvider;
import com.sun.jersey.multipart.impl.MultiPartConfigProvider;
import com.sun.jersey.multipart.impl.MultiPartReaderServerSide;
import com.sun.jersey.multipart.impl.MultiPartWriter;

@ApplicationPath("/services")
public class MyApplication extends Application {
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(JacksonJsonProvider.class);
        classes.add(HelloWorldService.class);
        classes.add(MultiPartWriter.class);
        classes.add(MultiPartReaderServerSide.class);
//        classes.add(MultiPartConfigProvider.class);
        classes.add(FormDataMultiPartDispatchProvider.class);
        
        return classes;
    }
}