package pl.edu.pjwstk.jaz.average;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
public class AverageController {

    @GetMapping("average")
        public String getAverage(@RequestParam(value = "numbers", required = false)String numbers) {


        if (numbers == null || numbers.equals("")) {
            return "Please put the parameters";
        }
        else {
            float count = 0;
            float result = 0;

            String[] parameters = numbers.split(",");



            for (String number:parameters) {

                    result += Integer.parseInt(number);
                    count++;

            }


            BigDecimal round = new BigDecimal(result / count);
            round = round.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();


            return "Average equals to: " + String.valueOf(round);
        }
    }
}
