<template>
  <v-card width="500" id="register" class="mx-auto mt-5 at-5">
    <v-card-title>
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
    </v-card-title>
    <v-card-text>
      <v-form ref="registrationForm">
        <v-text-field
          v-model="user.firstName"
          label="First Name"
          prepend-icon="mdi-account-circle"
          :rules="firstNameRules"
        />
        <v-text-field
          v-model="user.lastName"
          label="Last Name"
          prepend-icon="mdi-account-circle"
          :rules="lastNameRules"
        />
        <v-text-field
          v-model="user.username"
          label="Username"
          prepend-icon="mdi-account-circle"
          :rules="usernameRules"
        />
        <v-text-field
          v-model="user.email"
          type="text"
          label="Email"
          prepend-icon="mdi-email"
          :rules="emailRules"
        />
        <v-text-field
          v-model="user.password"
          :type="showPassword ? 'text' : 'password'"
          label="Password"
          prepend-icon="mdi-lock"
          :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="showPassword = !showPassword"
          :rules="passwordRules"
          error-count="5"
        />
        <v-text-field
          v-model="user.confirmPassword"
          :type="showConfirmPassword ? 'text' : 'password'"
          label="Please Confirm Your Password"
          prepend-icon="mdi-lock"
          :append-icon="showConfirmPassword ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="showConfirmPassword = !showConfirmPassword"
          :rules="confirmPasswordRules"
          @keydown.enter="register"
        />
      </v-form>
      <div id="errors" v-for="error in errors" :key="error">{{error}}</div>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions>
      <v-btn outlined color="#BB86FC" :to="{ name: 'login' }">Already Have an Account?</v-btn>
      <v-spacer></v-spacer>
      <v-btn outlined color="#03DAC5" type="submit" @click="register">Register</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: "",
        role: "user"
      },
      usernameRules: [v => !!v || "Username is required."],
      firstNameRules: [v => !!v || "First Name is required."],
      lastNameRules: [v => !!v || "Last Name is required."],
      emailRules: [
        v => !!v || "E-mail is required.",
        v => /.+@.+\..+/.test(v) || "E-mail must be valid."
      ],
      passwordRules: [
        v => !!v || "Password is required.",
        v => v.length >= 8 || "Password must be at least 8 characters.",
        v =>
          /(?=.*[A-Z])/.test(v) ||
          "Password must have one uppercase character.",
        v => /(?=.*\d)/.test(v) || "Password must have one number.",
        v =>
          /([!@$%])/.test(v) ||
          "Password must have one special character. [!@$%]"
      ],
      confirmPasswordRules: [
        v => v === this.user.password || "Passwords must match"
      ],
      registrationErrors: false,
      showPassword: false,
      showConfirmPassword: false,
      errors: []
    };
  },
  methods: {
    register() {
      if (this.$refs.registrationForm.validate()) {
        fetch(`${process.env.VUE_APP_REMOTE_API}/register`, {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json"
          },
          body: JSON.stringify(this.user)
        })
          .then(response => {
            if (response.ok) {
              return response.json();
            } else {
              this.registrationErrors = true;
              throw "Register returned: " + response.status;
            }
          })
          .then(parsedData => {
            if (parsedData.success) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" }
              });
            } else {
              this.registrationErrors = true;
              this.errors = parsedData.errors;
            }
          })
          .catch(err => console.log(err));
        this.$emit("update-user");
      }
    }
  }
};
</script>

<style scoped>
#errors {
  color: firebrick;
}
</style>
