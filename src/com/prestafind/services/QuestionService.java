package com.prestafind.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.prestafind.entities.Question;
import com.prestafind.entities.Utilisateurs;
import com.prestafind.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionService {

    public static QuestionService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Question> listQuestions;


    private QuestionService() {
        cr = new ConnectionRequest();
    }

    public static QuestionService getInstance() {
        if (instance == null) {
            instance = new QuestionService();
        }
        return instance;
    }

    public ArrayList<Question> getAll() {
        listQuestions = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/question");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listQuestions = getList();
                }

                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listQuestions;
    }

    private ArrayList<Question> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                int status = 0;
                if (obj.get("status").toString().equals("true")) status = 1;

                Question question = new Question(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        makeUtilisateurs((Map<String, Object>) obj.get("utilisateurs")),
                        (String) obj.get("sujet"),
                        (String) obj.get("description"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateCreation")),
                        status,
                        (String) obj.get("type")

                );

                listQuestions.add(question);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listQuestions;
    }

    public Utilisateurs makeUtilisateurs(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Utilisateurs utilisateurs = new Utilisateurs();
        utilisateurs.setId((int) Float.parseFloat(obj.get("id").toString()));
        return utilisateurs;
    }

    public int add(Question question) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/question/add");

        cr.addArgument("utilisateurs", String.valueOf(question.getUtilisateurs().getId()));
        cr.addArgument("sujet", question.getSujet());
        cr.addArgument("description", question.getDescription());
        cr.addArgument("dateCreation", new SimpleDateFormat("dd-MM-yyyy").format(question.getDateCreation()));
        cr.addArgument("status", String.valueOf(question.getStatus()));
        cr.addArgument("type", question.getType());


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int edit(Question question) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/question/edit");
        cr.addArgument("id", String.valueOf(question.getId()));

        cr.addArgument("utilisateurs", String.valueOf(question.getUtilisateurs().getId()));
        cr.addArgument("sujet", question.getSujet());
        cr.addArgument("description", question.getDescription());
        cr.addArgument("dateCreation", new SimpleDateFormat("dd-MM-yyyy").format(question.getDateCreation()));
        cr.addArgument("status", String.valueOf(question.getStatus()));
        cr.addArgument("type", question.getType());


        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public int delete(int questionId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/question/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(questionId));

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }
}
