package com.practice.util;

import com.practice.common.ConferenceTakeTimesEnum;
import com.practice.exception.InputIllegalException;
import com.practice.model.Conference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    /**
     * transform input line to entity Conference
     * @param input
     * @return
     * @throws InputIllegalException
     */
    public static Conference getConference(String input) throws InputIllegalException{

        if (input == null || input.isEmpty()){
            throw new InputIllegalException("{"+ input + "} is empty.");
        }

        String regex = "^([^0-9]+?)(((\\d+)min)?)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()){

            if (matcher.groupCount() != 4){
                throw new InputIllegalException("{" + input + "} is illegal input. input line format error");
            }

            String title = matcher.group(1).trim();
            Integer takeTimes;
            if (matcher.group(4) != null){
                takeTimes = Integer.valueOf(matcher.group(4));
            }else{
                if (title.endsWith(ConferenceTakeTimesEnum.LIGHTNING.getName())){
                    takeTimes = ConferenceTakeTimesEnum.LIGHTNING.getValue();
                }else{
                    throw new InputIllegalException("{" + input + "} is illegal input. " +
                            "because the talk length can be null only if title is lightning");
                }
            }

            if (takeTimes!=null && (takeTimes%5!=0)){
                throw new InputIllegalException("{" + input + "} is illegal input. because all talk lengths must be either in minutes (not hours) or lightning (5 minutes).");
            }

            return new Conference(input, title, takeTimes);
        }else {
            throw new InputIllegalException("regex not match for {" + input + "}. because talk title has numbers in it or all talk lengths are not in minutes");
        }
    }
}
