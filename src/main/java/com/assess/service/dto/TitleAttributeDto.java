package com.assess.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TitleAttributeDto implements Comparable<TitleAttributeDto>{
    private Integer titleBasic;
//    private int endYear;
    // job, nameBasic
    private Map<Integer, List<Integer>> principles = new HashMap<>();
    private Float averageRating;
    private Integer numVotes;

    public TitleAttributeDto(Integer titleBasic) {

        this.titleBasic = titleBasic;

    }

    @Override
    public int compareTo(TitleAttributeDto o) {
        int retVal = 0;
        try{
            if(this.getNumVotes() > o.getNumVotes() ){
                retVal = -1;
            }else if(this.getNumVotes() < o.getNumVotes()){
                retVal = 1;
            }else if(this.getAverageRating() > o.getAverageRating()){
                retVal = -1;
            }else if(this.getAverageRating() < o.getAverageRating()){
                retVal = 1;
            }

        }catch (Exception e){
            retVal= 0;
        }
        return retVal;
    }
}
