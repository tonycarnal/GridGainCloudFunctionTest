package java.com.gridgainexemple;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

import java.io.BufferedWriter;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class cachetest implements HttpFunction {
    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {

        ClientConfiguration cfg = new ClientConfiguration().setAddresses("34.83.119.208:10800");
        String result;
        try (IgniteClient client = Ignition.startClient(cfg)) {
            ClientCache<Integer, String> cache = client.cache("myCache");
            Map<Integer, String> data = IntStream.rangeClosed(1, 100).boxed()
                    .collect(Collectors.toMap(i -> i, Object::toString));


            cache.putAll(data);

            assert !cache.replace(1, "2", "3");
            assert "1".equals(cache.get(1));
            assert cache.replace(1, "1", "3");
            assert "3".equals(cache.get(1));

            cache.put(101, "101");

            cache.removeAll(data.keySet());
            assert cache.size() == 1;
            assert "101".equals(cache.get(101));

            cache.removeAll();
            assert 0 == cache.size();
        }
        catch (ClientException e) {
            System.err.println(e.getMessage());
        }


        BufferedWriter writer = response.getWriter();
        writer.write("Hello world!");
    }
}
