<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn flat text v-on="on">
          <v-icon small>mdi-lock-reset</v-icon>Reset Password
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Reset Password</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  v-model="user.oldPassword"
                  :type="showOldPassword ? 'text' : 'password'"
                  label="Old Password"
                  :append-icon="showOldPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showOldPassword = !showOldPassword"
                  required
                />
                <v-text-field
                  v-model="user.newPassword"
                  :type="showNewPassword ? 'text' : 'password'"
                  label="New Password"
                  :append-icon="showNewPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showNewPassword = !showNewPassword"
                  required
                />
                <v-text-field
                  v-model="user.confirmPassword"
                  :type="showConfirmPassword ? 'text' : 'password'"
                  label="Confirm Password"
                  :append-icon="showConfirmPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showConfirmPassword = !showConfirmPassword"
                  required
                />
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="dialog = false">Reset</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
      showConfirmPassword: false,
      showOldPassword: false,
      showNewPassword: false,
      user: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: ""
      }
    };
  }
};
</script>

<style>
</style>