package contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@IntegrationComponentScan
public class ContactsApplication {

    public static void main(String[] args) throws Exception {
        String sDate2 = "Dec 19, 1998";
        SimpleDateFormat formatter2=new SimpleDateFormat("MMM dd, yyyy",Locale.US);
        Date date2=formatter2.parse(sDate2);
        System.out.println(sDate2+"\t"+date2);

        SimpleDateFormat fromat2 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(fromat2.format(date2) +"------===-ewewew=-=- ");

        SpringApplication.run(ContactsApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
