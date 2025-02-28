package com.prestafind.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.prestafind.entities.Question;
import com.prestafind.entities.Reponse;
import com.prestafind.entities.Utilisateurs;
import com.prestafind.utils.Statics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReponseService {

    public static ReponseService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Reponse> listReponses;


    private ReponseService() {
        cr = new ConnectionRequest();
    }

    public static ReponseService getInstance() {
        if (instance == null) {
            instance = new ReponseService();
        }
        return instance;
    }

    public ArrayList<Reponse> getAll() {
        listReponses = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/reponse");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listReponses = getList();
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

        return listReponses;
    }

    private ArrayList<Reponse> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Reponse reponse = new Reponse(
                        (int) Float.parseFloat(obj.get("id").toString()),

                        makeUtilisateurs((Map<String, Object>) obj.get("utilisateurs")),
                        makeQuestion((Map<String, Object>) obj.get("question")),
                        (String) obj.get("contenu"),
                        new SimpleDateFormat("dd-MM-yyyy").parse((String) obj.get("dateCreation"))

                );

                listReponses.add(reponse);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return listReponses;
    }

    public Utilisateurs makeUtilisateurs(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Utilisateurs utilisateurs = new Utilisateurs();
        utilisateurs.setId((int) Float.parseFloat(obj.get("id").toString()));
        return utilisateurs;
    }

    public Question makeQuestion(Map<String, Object> obj) {
        if (obj == null) {
            return null;
        }
        Question question = new Question();
        question.setId((int) Float.parseFloat(obj.get("id").toString()));
        question.setSujet((String) obj.get("sujet"));
        return question;
    }

    public int add(Reponse reponse) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/reponse/add");

        cr.addArgument("utilisateurs", String.valueOf(reponse.getUtilisateurs().getId()));
        cr.addArgument("question", String.valueOf(reponse.getQuestion().getId()));
        cr.addArgument("contenu", reponse.getContenu());
        cr.addArgument("dateCreation", new SimpleDateFormat("dd-MM-yyyy").format(reponse.getDateCreation()));


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

    public int edit(Reponse reponse) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/reponse/edit");
        cr.addArgument("id", String.valueOf(reponse.getId()));

        cr.addArgument("utilisateurs", String.valueOf(reponse.getUtilisateurs().getId()));
        cr.addArgument("question", String.valueOf(reponse.getQuestion().getId()));
        cr.addArgument("contenu", reponse.getContenu());
        cr.addArgument("dateCreation", new SimpleDateFormat("dd-MM-yyyy").format(reponse.getDateCreation()));


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

    public int delete(int reponseId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/reponse/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(reponseId));

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
