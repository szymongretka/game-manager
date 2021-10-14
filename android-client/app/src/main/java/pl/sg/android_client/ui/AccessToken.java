package pl.sg.android_client.ui;

import com.google.gson.annotations.SerializedName;

public class AccessToken {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("expires_in")
    private Integer expires_in;
    @SerializedName("refresh_expires_in")
    private Integer refresh_expires_in;
    @SerializedName("refresh_token")
    private String refresh_token;
    @SerializedName("token_type")
    private String token_type;
    @SerializedName("id_token")
    private String id_token;
    @SerializedName("not-before-policy")
    private Integer notBeforePolicy;
    @SerializedName("session_state")
    private String session_state;
    @SerializedName("scope")
    private String scope;

    public AccessToken(String accessToken, Integer expires_in) {
        this.accessToken = accessToken;
        this.expires_in = expires_in;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

}
