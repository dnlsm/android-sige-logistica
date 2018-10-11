package br.com.cpqd.instrumentacao.sige.services;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProtocolService{

    @POST("protocol")
    Call<Void> listAllProtocols();

    @POST("protocol/new-protocol-api-router")
    Call<Void> newProtocol();

    @POST("protocol/list-protocol-api-router")
    Call<Void> listProtocol();

    @DELETE("protocol/delete-protocol-api-router")
    Call<Void> deleteProtocol();

    @POST("protocol/update-protocol-api-router")
    Call<Void> updateProtocol();


}
