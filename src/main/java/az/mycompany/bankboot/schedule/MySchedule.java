package az.mycompany.bankboot.schedule;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class MySchedule {


//    @Scheduled(fixedDelay = 6000)
//    @Scheduled(fixedRate = 6000)
//    public void start(){
//        System.out.println("Fixed Delay: " + System.currentTimeMillis());
//    }

//    @Async
//    @Scheduled(fixedDelay = 1000)
//    public void start(int a){
//        System.out.println("Fixed Delay: " + System.currentTimeMillis());
//        a++;
//    }


    //Fixed Delay - proseesin bitmesini gozleyir
    // eger proses onun gozleme vaxtini kecibse biten kimi icra olunur
    //Fixed Rate - prosesin bitmesini gozlemir avto icra olunur connection aciq qalsa bele
    //Initial Delay - proses bitdikden sonra eger fixed delay vaxti bitibse
    // 2 saniye gozlemeknen icra olunur , bitmeyibse birinci fixeddelay gozlenilir sonra init delay


//    @Async
//    @Scheduled(fixedDelay = 1000 , initialDelay = 2000)
//    public void start(){
//        System.out.println("Fixed Delay: " + System.currentTimeMillis());
//    }

    @Scheduled(cron = "0 0 * * * *",zone = "Europe/Paris")
    public void start(){
        System.out.println("Fixed Delay: " + System.currentTimeMillis() / 1000);
    }
}
