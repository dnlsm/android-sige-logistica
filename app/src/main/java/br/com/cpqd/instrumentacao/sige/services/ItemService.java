package br.com.cpqd.instrumentacao.sige.services;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.dto.ItemDto;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ItemService{

    @POST("inner/item/list")
    Call<ItemDto> listAllItems(@Query("token") String token);

    @POST("inner/item/new")
    Call<Void> newItem(@Query("token") String token);

    @POST("inner/item")
    Call<ItemDto> listItem(@Query("token") String token, @Query("item") String itemCode);

    @DELETE("inner/item/delete")
    Call<Void> deleteItem(@Query("token") String token);

    @POST("inner/item/update")
    Call<Void> updateItem(@Query("token") String token);


}
