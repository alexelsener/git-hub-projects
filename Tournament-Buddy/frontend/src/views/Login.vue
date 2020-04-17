<template>
  <v-card width="450" id="login" class="mx-auto mt-5 at-5">
    <v-card-title>
      <h1 class="h3 mb-3 font-weight-normal">Please Sign In</h1>
    </v-card-title>
    <v-card-text>
      <v-form class="form-signin" @submit.prevent="login">
        <v-text-field
          v-model="user.username"
          label="Username"
          prepend-icon="mdi-account-circle"
          :rules="usernameRules"
          color="#03DAC5"
        />
        <v-text-field
          v-model="user.password"
          :type="showPassword ? 'text' : 'password'"
          label="Password"
          id="password"
          prepend-icon="mdi-lock"
          :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="showPassword = !showPassword"
          :rules="passwordRules"
          color="#03DAC5"
          @keydown.enter="login"
        />
      </v-form>
      <div id="invalidCredentials" v-if="invalidCredentials">Incorrect Username or Password.</div>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions>
      <v-btn outlined color="#BB86FC" :to="{ name: 'register' }">Not Registered?</v-btn>
      <v-spacer></v-spacer>
      <v-btn outlined color="#03DAC5" type="submit" @click="login" @keydown.enter="login">Log In</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import auth from "../auth";

export default {
  name: "login",
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      usernameRules: [v => !!v || "Username is required."],
      passwordRules: [v => !!v || "Password is required."],
      showPassword: false,
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/login`, {
        method: "POST",
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.user)
      })
        .then(response => {
          if (response.ok) {
            return response.text();
          } else {
            this.invalidCredentials = true;
          }
        })
        .then(token => {
          if (token != undefined) {
            if (token.includes('"')) {
              token = token.replace(/"/g, "");
            }
            auth.saveToken(token);
            this.$router.push("/user-info");
            this.$emit("update-user");
          }
        })
        .catch(err => console.error(err));
    }
  }
};
</script>

<style scoped>
#invalidCredentials {
  color: firebrick;
}
</style>
