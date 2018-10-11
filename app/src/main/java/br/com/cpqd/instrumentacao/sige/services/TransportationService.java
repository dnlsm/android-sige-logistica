package br.com.cpqd.instrumentacao.sige.services;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.dto.TransportationDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TransportationService{

    @POST("inner/transportation/list")
    Call<TransportationDto> listAllTransportations(@Query("token") String token);

    @POST("inner/transportation")
    Call<TransportationDto> listTransportation(@Query("token") String token, @Query("transportation_code") String transportationCode);

}
