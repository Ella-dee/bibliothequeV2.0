package com.mclientui.microserviceclientui.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;


public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String invoqueur, Response reponse) {

        ObjectMapper mapper = new ObjectMapper();
        try{
            ApiError error = mapper.readValue(reponse.body().asInputStream(), ApiError.class);
            switch (error.getMessage()){
                case "WaitingList01":
                    return new CannotAddException("Utilisateur déjà sur liste d'attente");
                case "WaitingList02":
                    return new CannotAddException(" Utilisateur a déjà un prêt en cours pour ce livre");
                case "WaitingList03":
                    return new CannotAddException("Liste d'attente déjà trop longue");
                case "User03":
                    return new CannotAddException("");
                case "User01":
                    return new BadLoginPasswordException("L'utilisateur n'existe pas");
                case "User02":
                    return new BadLoginPasswordException("L'association login/mot de passe est invalide");
                default: return defaultErrorDecoder.decode(invoqueur, reponse);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
       return defaultErrorDecoder.decode(invoqueur, reponse);
    }
}
