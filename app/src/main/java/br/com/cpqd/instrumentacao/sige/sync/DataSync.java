package br.com.cpqd.instrumentacao.sige.sync;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.cpqd.instrumentacao.sige.activities.HomeActivity;
import br.com.cpqd.instrumentacao.sige.activities.ItemInfoActivity;
import br.com.cpqd.instrumentacao.sige.activities.LoginActivity;
import br.com.cpqd.instrumentacao.sige.activities.SplashActivity;
import br.com.cpqd.instrumentacao.sige.activities.LoadingActivity;
import br.com.cpqd.instrumentacao.sige.adapters.HomeAdapter;
import br.com.cpqd.instrumentacao.sige.dto.AuthDto;
import br.com.cpqd.instrumentacao.sige.dto.ItemDto;
import br.com.cpqd.instrumentacao.sige.dto.MovementsDto;
import br.com.cpqd.instrumentacao.sige.dto.TransportationDto;
import br.com.cpqd.instrumentacao.sige.dto.UserDto;
import br.com.cpqd.instrumentacao.sige.models.Item;
import br.com.cpqd.instrumentacao.sige.models.Movements;
import br.com.cpqd.instrumentacao.sige.models.Transportation;
import br.com.cpqd.instrumentacao.sige.models.UserCategory;
import br.com.cpqd.instrumentacao.sige.preferences.UserPreferences;
import br.com.cpqd.instrumentacao.sige.refrofit.RetrofitInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSync implements Serializable {
    private static final String INVALID_TOKEN = "Invalid token";
    private ItemInfoActivity itemInfoActivity;
    private LoadingActivity loadingActivity;
    private LoginActivity loginActivity;
    private SplashActivity splashActivity;
    private HomeActivity homeActivity;
    private Context context;
    private String token;
    private List<Item> itens = new ArrayList<>();
    private List<Object> objects = new ArrayList<>();
    private List<Transportation> transportations = new ArrayList<>();
    private transient Call<AuthDto> authCall;
    private transient Call<ItemDto> loadItemCall;
    private UserDto userDto;
    private String userLogin;
    private String userPlace;
    private UserPreferences preferences;
    private TextView userInfo;
    private TextView labInfo;
    private transient Call<TransportationDto> transportationCall;
    private transient Call<UserDto> credentialsCall;
    private AuthDto auth;
    private int status_code;
    private Call<MovementsDto> movementsCall;
    private List<Movements> movementsList;
    private UserCategory userCategory;

    public DataSync(Activity activity, Context context) {
        if (activity.getClass() == HomeActivity.class) {
            this.homeActivity = (HomeActivity) activity;
        } else if (activity.getClass() == SplashActivity.class) {
            this.splashActivity = (SplashActivity) activity;
        } else if (activity.getClass() == LoginActivity.class) {
            this.loginActivity = (LoginActivity) activity;
        }else if(activity.getClass() == LoadingActivity.class){
            this.loadingActivity = (LoadingActivity) activity;
        }else if(activity.getClass() == ItemInfoActivity.class){
            this.itemInfoActivity = (ItemInfoActivity) activity;
        }
        this.context = context;
        this.preferences = new UserPreferences(this.context);
    }

    public void fillAndSetAdapter() {
        loadTransportationList();
    }

    public AuthDto getAuth(){
        return this.auth;
    }

    public void authentication(String user, String pwd) {
        System.out.println("entrei na authentication");
        authCall = new RetrofitInitializer().getUserService().authentication(user, pwd);
        try {
            authCall.enqueue(new Callback<AuthDto>() {
                @Override
                public void onResponse(Call<AuthDto> call, Response<AuthDto> response) {
                    Log.i("resposta ", String.valueOf(response.body()));
                    auth = response.body();
                    if (auth == null) {
                        Log.e("error", "Servidor indisponível");
                    } else {
                        if (Integer.valueOf(auth.getStatus_code()) == 200) {
                            setStatus_code(Integer.valueOf(auth.getStatus_code()));
                            System.out.println("SETEI O STATUS_CODE PARA: " + getStatus_code());
                            String string = auth.getRetorno().getToken();
                            setToken(auth.getRetorno().getToken());
                            Log.i("token ", getToken());
                            preferences = new UserPreferences(context);
                            preferences.SaveToken(auth.getRetorno().getToken());
                            delay();
                        } else {
                            Log.e("erro ", auth.getMsg() + " - " + auth.getStatus_code());
                            setStatus_code(Integer.valueOf(auth.getStatus_code()));
                        }
                    }
                    System.out.println("ACABOU O AUTHENTICATION");
                }

                @Override
                public void onFailure(Call<AuthDto> call, Throwable t) {
                    Log.e("error ", t.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delay() {
        int i = 0;
        while (i < 500) {
            i++;
        }
    }

    public boolean isThereAToken() {
        preferences = new UserPreferences(context);
        if (preferences.getUserToken().equals("token not found")) {
            Log.e("error", "token not found");
            return false;
        } else {
            Log.i("msg", "token found");
            setToken(preferences.getUserToken());
            return true;
        }
    }

    private void loadTransportationList() {
        System.out.println("entrei no transportation list");
        System.out.println("token: " + getToken());
        transportationCall = new RetrofitInitializer().getTransportationService().listAllTransportations(preferences.getUserToken());
        transportationCall.enqueue(new Callback<TransportationDto>() {
            @Override
            public void onResponse(Call<TransportationDto> call, Response<TransportationDto> response) {
                Log.i("Resposta", String.valueOf(response.body()));
                TransportationDto transportationDto = response.body();
                if (transportationDto == null) {
                    Log.e("error", "Servidor indisponível");
                } else {
                    if (Integer.valueOf(transportationDto.getStatus_code()) == 200) {
                        setStatus_code(Integer.valueOf(transportationDto.getStatus_code()));
                        transportations = transportationDto.getRetorno();
                        for (Transportation transportation :
                                transportations) {
                            Log.i("transporte", transportation.getCode() + " - " + transportation.getFrom() + " - " + transportation.getTo() + " - " + transportation.getCreated_date());
                        }
                        objects.addAll(transportations);
                        delay();
                        loadItemList();
                    } else if (Integer.valueOf(transportationDto.getStatus_code()) == 401 && transportationDto.getMsg().equals(INVALID_TOKEN)) {
                        setStatus_code(Integer.valueOf(transportationDto.getStatus_code()));
                        System.out.println("TRANSPORTATION LIST-> SETEI O STATUS_CODE PARA: " + getStatus_code());
                        //voltar para loginactivity
                    } else {
                        Log.e("error ", transportationDto.getMsg() + " - " + transportationDto.getStatus_code());
                    }
                }
            }

            @Override
            public void onFailure(Call<TransportationDto> call, Throwable t) {
                Log.e("error ", t.getMessage());
            }
        });
    }

    private void loadItemList() {
        System.out.println("entrei na item list");
        loadItemCall = new RetrofitInitializer().getItemService().listAllItems(preferences.getUserToken());
        loadItemCall.enqueue(new Callback<ItemDto>() {
            @Override
            public void onResponse(Call<ItemDto> call, Response<ItemDto> response) {
                Log.i("Resposta", String.valueOf(response.body()));
                ItemDto itemDto = response.body();
                if (itemDto == null) {
                    Log.e("error", "Servidor indisponível");
                } else {
                    if (Integer.valueOf(itemDto.getStatus_code()) == 200) {
                        setStatus_code(Integer.valueOf(itemDto.getStatus_code()));
                        System.out.println("SETEI O STATUS_CODE PARA: " + getStatus_code());
                        itens = itemDto.getRetorno();
                        for (Item item :
                                itens) {
                            Log.i("item", item.toString());
                        }
                        objects.addAll(itens);
                        HomeAdapter adapter = new HomeAdapter(objects, homeActivity, homeActivity);
                        homeActivity.getRecyclerView().setAdapter(adapter);
                        homeActivity.setSearchButton(adapter);
                    } else if (Integer.valueOf(itemDto.getStatus_code()) == 401 && itemDto.getMsg().equals(INVALID_TOKEN)) {
                        setStatus_code(Integer.valueOf(itemDto.getStatus_code()));
                        //voltar para loginactivity
                    } else {
                        Log.e("erro ", itemDto.getMsg() + " - " + itemDto.getStatus_code());
                    }
                }
                System.out.println("ACABOU O CONSULT USER");
            }

            @Override
            public void onFailure(Call<ItemDto> call, Throwable t) {
                Log.e("error ", t.getMessage());
            }
        });
    }

    public void findItemMovements(int code) {

        movementsCall = new RetrofitInitializer().getMovementsService().listMovements(preferences.getUserToken(), code);
        movementsCall.enqueue(new Callback<MovementsDto>() {
            @Override
            public void onResponse(Call<MovementsDto> call, Response<MovementsDto> response) {
                Log.i("sucess", String.valueOf(response.body()));
                MovementsDto movementsDto = response.body();
                try{
                    setStatus_code(movementsDto.getStatus_code());
                    if(movementsDto.getStatus_code() == 200){
                        movementsList = movementsDto.getRetorno();
                        for (Movements movement :
                                movementsList) {
                            Log.i("movement: ", movement.toString());//devolver movement from e to
                        }
                    }else if(movementsDto.getStatus_code() == 401){
                        movementsList = new ArrayList<>();
                    }
                }catch(Exception e){
                    setStatus_code(500);
                }

            }

            @Override
            public void onFailure(Call<MovementsDto> call, Throwable t) {
                Log.e("error ", t.getMessage());
                movementsList = new ArrayList<>();
                setStatus_code(500);
            }
        });
    }

    public void consultUserInfo() {
        credentialsCall = new RetrofitInitializer().getUserService().credentials(preferences.getUserToken());
        credentialsCall.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                userDto = response.body();
                if (userDto == null) {
                    Log.e("error", "Servidor indisponível");
                } else {
                    if (Integer.valueOf(userDto.getStatus_code()) == 200) {
                        userLogin = userDto.getRetorno().getLogin();
                        userPlace = userDto.getRetorno().getPlace();
                        setUserCategory(userDto.getRetorno().getCategory());
                        setStatus_code(Integer.valueOf(userDto.getStatus_code()));
                        System.out.println("SETEI O STATUS_CODE PARA: " + getStatus_code());
                        Log.i("resposta ", userDto.getRetorno().toString());
                    } else if (Integer.valueOf(userDto.getStatus_code()) == 401 && userDto.getMsg().equals(INVALID_TOKEN)) {
                        setStatus_code(Integer.valueOf(userDto.getStatus_code()));
                        //voltar para loginactivity
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Log.e("error ", t.getMessage());
            }
        });

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPlace() {
        return userPlace;
    }

    public void setUserPlace(String userPlace) {
        this.userPlace = userPlace;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<Movements> getMovements(){
        if(movementsList == null){
            return movementsList = new ArrayList<>();
        }else{
            return movementsList;
        }
    }

    public UserCategory getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(UserCategory userCategory) {
        this.userCategory = userCategory;
    }
}