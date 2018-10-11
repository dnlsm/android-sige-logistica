package br.com.cpqd.instrumentacao.sige.services;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.dto.AuthDto;
import br.com.cpqd.instrumentacao.sige.dto.UserDto;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService{

    @POST("auth")
    Call<AuthDto> authentication(@Query("usr") String usr, @Query("pwd") String pwd);

    @POST("inner")
    Call<UserDto> credentials(@Query("token") String token);

}
