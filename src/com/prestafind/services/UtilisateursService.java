package com.prestafind.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.*;
import com.codename1.ui.events.ActionListener;
import com.prestafind.entities.Utilisateurs;
import com.prestafind.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UtilisateursService {

    public static UtilisateursService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Utilisateurs> listUtilisateurss;


    private UtilisateursService() {
        cr = new ConnectionRequest();
    }

    public static UtilisateursService getInstance() {
        if (instance == null) {
            instance = new UtilisateursService();
        }
        return instance;
    }

    public ArrayList<Utilisateurs> getAll() {
        listUtilisateurss = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/utilisateurs");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listUtilisateurss = getList();
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

        return listUtilisateurss;
    }

    private ArrayList<Utilisateurs> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Utilisateurs utilisateurs = new Utilisateurs(
                        (int) Float.parseFloat(obj.get("id").toString())
                );

                listUtilisateurss.add(utilisateurs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listUtilisateurss;
    }

    public int add(Utilisateurs utilisateurs) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/utilisateurs/add");

        cr.addArgument("id", String.valueOf(utilisateurs.getId()));


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

    public int edit(Utilisateurs utilisateurs) {

        cr = new ConnectionRequest();
        cr.setHttpMethod("POST");
        cr.setUrl(Statics.BASE_URL + "/utilisateurs/edit");
        cr.addArgument("id", String.valueOf(utilisateurs.getId()));

        cr.addArgument("id", String.valueOf(utilisateurs.getId()));


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

    public int delete(int utilisateursId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL + "/utilisateurs/delete");
        cr.setHttpMethod("POST");
        cr.addArgument("id", String.valueOf(utilisateursId));

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
