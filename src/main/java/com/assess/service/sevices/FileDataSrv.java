package com.assess.service.sevices;

import com.assess.common.component.FileDataStructure;
import com.assess.common.component.readpurefile.IFileReader;
import com.assess.common.exception.BusinessCodeException;
import com.assess.common.form.OutputAPIForm;
import com.assess.common.utilities.GeneralUtility;
import com.assess.common.threads.SearchDataThread;
import com.assess.common.threads.SearchDataThreadSecond;
import com.assess.service.dto.TitleAttributeDto;
import com.assess.service.dto.TitleBasicsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class FileDataSrv implements IFileDataSrv {
    @Autowired
    private FileDataStructure fileDataStructure;
    @Autowired
    private IFileReader fileReaderTitleBasic;
    @Autowired
    private IFileReader fileReaderNameBasics;
    @Autowired
    private IFileReader fileReaderTileRatings;
    @Autowired
    private IFileReader fileReaderTitleCrew;
    @Autowired
    private IFileReader fileReaderTitlePrincipals;

    private Vector<TitleAttributeDto> titleAttributesFirstQuestion = new Vector<>();
    private Vector<TitleAttributeDto> titleAttributesSecondQuestion = new Vector<>();
    private Map<Integer,TitleAttributeDto> titleAttributesThirdQuestion = new HashMap<>();


    public void readAllFiles(){
        fileDataStructure.clear();
        fileReaderTitleBasic.readFile("title.basics.tsv");
        fileReaderNameBasics.readFile("name.basics.tsv");
        fileReaderTileRatings.readFile("title.ratings.tsv");
        fileReaderTitleCrew.readFile("title.crew.tsv");
        fileReaderTitlePrincipals.readFile("title.principals.tsv");
    }



    public void searchFirstQuestion(Map<Integer, List<Integer>> index, Map<Integer, TitleAttributeDto> data, Vector<TitleAttributeDto> retVal){
        index.keySet().stream().forEach(year->{
            index.get(year).stream().map(id ->data.get(id)).forEach(currentData ->{
                try{
                    List<Integer> writers = currentData.getPrinciples().get(fileDataStructure.getBaseJob().get("writer"));
                    List<Integer> director = currentData.getPrinciples().get(fileDataStructure.getBaseJob().get("director"));
                    if(Objects.nonNull(writers) && Objects.nonNull(director)){
                        ArrayList<Integer> commonList = new ArrayList<>(writers);
                        commonList.retainAll(director);
                        if(Objects.nonNull(commonList) &&  commonList.size() > 0 ){
                            for(Integer nameId:commonList){
                                if(Objects.isNull(fileDataStructure.getNameBasicCache().get(nameId)) || fileDataStructure.getNameBasicCache().get(nameId).equals(-1)){
                                    retVal.add(currentData) ;
                                    break;
                                }
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        });
    }

    public void searchSecondQuestion(Map<Integer, List<Integer>> index, Map<Integer, TitleAttributeDto> data,String firstActor,String secondActor,Vector<TitleAttributeDto> retVal){
        index.keySet().stream().forEach(year->{
            index.get(year).stream().map(id ->data.get(id)).forEach(currentData ->{
                try{
                    List<Integer> actors = currentData.getPrinciples().get(fileDataStructure.getBaseJob().get("actor"));
                    if(Objects.nonNull(actors)){
                        if(actors.contains(GeneralUtility.convertToId(firstActor)) && actors.contains(GeneralUtility.convertToId(secondActor))){
                            retVal.add(currentData) ;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        });
    }

    public void searchFirstQuestion(){
        ExecutorService executor = Executors.newFixedThreadPool(fileDataStructure.getTitleBasicsIndex().keySet().size());
        titleAttributesFirstQuestion = new Vector<>();
        SearchDataThread thread;
        for(String genre:fileDataStructure.getTitleBasicsIndex().keySet()){
            thread = new SearchDataThread(fileDataStructure,this,genre,titleAttributesFirstQuestion);
            executor.execute(thread);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(600, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public OutputAPIForm<List<TitleAttributeDto>> searchFirstQuestion(Integer page, Integer pageSize){
        OutputAPIForm<List<TitleAttributeDto>> retVal = new OutputAPIForm();
        try{
            if(page.equals(0)){
                searchFirstQuestion();
            }
            if(getTitleAttributesFirstQuestion().size() > page * pageSize){
                retVal.setData(getTitleAttributesFirstQuestion().subList(page * pageSize,Math.min((page * pageSize) + pageSize,getTitleAttributesFirstQuestion().size())));
            }
            if(getTitleAttributesFirstQuestion().size() > ((page * pageSize) + pageSize)){
                retVal.setNextPage(true);
            }

        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.SYSTEM_EXCEPTION);
        }
        return retVal;
    }

    public void searchSecondQuestion(String firstActor,String secondActor){
        ExecutorService executor = Executors.newFixedThreadPool(fileDataStructure.getTitleBasicsIndex().keySet().size());
        titleAttributesSecondQuestion = new Vector<>();
        SearchDataThreadSecond thread;
        for(String genre:fileDataStructure.getTitleBasicsIndex().keySet()){
            thread = new SearchDataThreadSecond(fileDataStructure,this,firstActor,secondActor,genre,titleAttributesSecondQuestion);
            executor.execute(thread);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(600, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }


    public OutputAPIForm<List<TitleAttributeDto>> searchSecondQuestion(String firstActor,String secondActor,Integer page,Integer pageSize){
        OutputAPIForm<List<TitleAttributeDto>> retVal = new OutputAPIForm();
        try{
            if(page.equals(0)){
                searchSecondQuestion(firstActor,secondActor);
            }
            if(getTitleAttributesSecondQuestion().size() > page * pageSize){
                retVal.setData(getTitleAttributesSecondQuestion().subList(page * pageSize,Math.min((page * pageSize) + pageSize,getTitleAttributesSecondQuestion().size())));
            }
            if(getTitleAttributesSecondQuestion().size() > ((page * pageSize) + pageSize)){
                retVal.setNextPage(true);
            }

        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.SYSTEM_EXCEPTION);
        }
        return retVal;
    }

    public void searchThirdQuestion(String genre){
        titleAttributesThirdQuestion = new HashMap<>();
        fileDataStructure.getTitleBasicsIndex().get(genre).keySet().stream().forEach(year->{
            ArrayList<TitleAttributeDto> values = new ArrayList<>();
            for(Integer id:fileDataStructure.getTitleBasicsIndex().get(genre).get(year)){
                values.add(fileDataStructure.getLineCache().get(id));
            }
            Collections.sort(values);
            titleAttributesThirdQuestion.put(year,values.get(0));
        });
    }
    public OutputAPIForm<List<TitleAttributeDto>> searchThirdQuestion(String genre,Integer page,Integer pageSize){
        OutputAPIForm<List<TitleAttributeDto>> retVal = new OutputAPIForm<>() ;
        try{
            if(page.equals(0)){
                searchThirdQuestion(genre);
            }
            List<TitleAttributeDto> third= getTitleAttributesThirdQuestion().values().stream().toList();
            if(third.size() > page * pageSize){
                retVal.setData(third.subList(page * pageSize,Math.min((page * pageSize) + pageSize,third.size())));
            }
            if(third.size() > ((page * pageSize) + pageSize)){
                retVal.setNextPage(true);
            }

        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(BusinessCodeException.SYSTEM_EXCEPTION);
        }

       return retVal;
    }

    public Vector<TitleAttributeDto> getTitleAttributesFirstQuestion() {
        return titleAttributesFirstQuestion;
    }

    public Vector<TitleAttributeDto> getTitleAttributesSecondQuestion() {
        return titleAttributesSecondQuestion;
    }

    public Map<Integer, TitleAttributeDto> getTitleAttributesThirdQuestion() {
        return titleAttributesThirdQuestion;
    }
}
