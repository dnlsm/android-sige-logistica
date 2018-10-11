package br.com.cpqd.instrumentacao.sige.refrofit;

import java.io.Serializable;

import br.com.cpqd.instrumentacao.sige.services.ItemService;
import br.com.cpqd.instrumentacao.sige.services.MovementsService;
import br.com.cpqd.instrumentacao.sige.services.ProtocolService;
import br.com.cpqd.instrumentacao.sige.services.TransportationService;
import br.com.cpqd.instrumentacao.sige.services.UserService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInitializer implements Serializable {

    private final Retrofit retrofit;

    public RetrofitInitializer(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://sige.cpqd.com.br/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public ItemService getItemService(){
        return retrofit.create(ItemService.class);
    }

    public MovementsService getMovementsService(){
        return retrofit.create(MovementsService.class);
    }

    public UserService getUserService(){
        return retrofit.create(UserService.class);
    }

    public ProtocolService getProtocolService(){
        return retrofit.create(ProtocolService.class);
    }

    public TransportationService getTransportationService(){
        return retrofit.create(TransportationService.class);
    }

}
