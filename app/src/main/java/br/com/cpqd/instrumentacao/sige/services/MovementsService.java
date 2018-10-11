package br.com.cpqd.instrumentacao.sige.services;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.dto.MovementsDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MovementsService {

    @POST("inner/item/movement/list")
    Call<MovementsDto> listAllMovements(@Query("token") String token);

    @POST("inner/item/movement")
    Call<MovementsDto> listMovements(@Query("token") String token, @Query("item_code") int itemCode);

    @POST("movements/update-movement-api-router")
    Call<Void> updateMovements();
}
