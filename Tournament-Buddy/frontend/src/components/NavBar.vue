<template>
  <v-app-bar app prominent text src="@/assets/Banner.png">
    <v-container>
      <v-row>
        <!-- nav bar- row tag start -->
        <!-- https://i.picsum.photos/id/353/6016/3376.jpg -->

        <v-col md="6" cols="12" class="pa-1">
          <div class="d-flex align-center clickable">
            <v-img
              alt="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
              class="shrink mr-2"
              contain
              src="@/assets/tournLogo.png"
              transition="scale-transition"
              width="40"
              @click="$router.push('/')"
            />
            <h1
              class="d-flex align-center pa-1 clickable"
              @click="$router.push('/')"
            >Tournament Buddy</h1>
          </div>
        </v-col>

        <v-row
          v-if="user === null || user === undefined"
          class="d-flex justify-center align-start pa-1"
          dense
          align-end
        >
          <router-link to="/login" class="d-flex justify-center align-start pa-1">
            <v-col cols="4" class="d-flex justify-center align-start">
              <v-btn text v-if="user === null || user === undefined">
                <v-icon>mdi-account</v-icon>Login
              </v-btn>
            </v-col>
          </router-link>
          <router-link to="/register" class="d-flex justify-center align-start pa-1">
            <v-col cols="4" class="d-flex justify-center align-start">
              <v-btn target="_blank" text v-if="user === null || user === undefined">
                <v-icon>mdi-new-box</v-icon>Register
              </v-btn>
            </v-col>
          </router-link>
        </v-row>

        <v-row
          v-if="user !== null && user !== undefined"
          class="d-flex justify-center align-center pa-1"
        >
          <v-menu offset-y v-if="user !== null && user !== undefined">
            <template v-slot:activator="{ on }">
              <v-col cols="4" class="d-flex justify-center">
                <v-btn app target="_blank" text v-on="on">
                  <v-icon>mdi-account-group</v-icon>Teams
                </v-btn>
              </v-col>
            </template>
            <v-list>
              <v-list-item>
                <h4 @click="$router.push('/browse-teams')" class="clickable">Browse Teams</h4>
              </v-list-item>
              <v-list-item>
                <h4 @click="$router.push('/create-team')" class="clickable">Create Team</h4>
              </v-list-item>
              <v-divider></v-divider>
              <v-list-item v-for="(item, index) in teams" :key="index">
                <v-list-item-title
                  class="clickable"
                  @click="updateTeam(item.teamId)"
                >{{ item.teamName }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>

          <v-menu offset-y v-if="user !== null && user !== undefined">
            <template v-slot:activator="{ on }">
              <v-col cols="4" class="d-flex justify-center align-center">
                <v-btn target="_blank" text v-on="on">
                  <v-icon>mdi-trophy</v-icon>Tournaments
                </v-btn>
              </v-col>
            </template>
            <v-list>
              <v-list-item>
                <h4
                  class="clickable"
                  @click="$router.push('/browse-tournaments')"
                >Browse Tournaments</h4>
              </v-list-item>
              <v-list-item>
                <h4 class="clickable" @click="$router.push('/create-tournament')">Create Tournament</h4>
              </v-list-item>
              <v-divider></v-divider>
              <v-list-item v-for="(item, index) in tournaments" :key="index">
                <v-list-item-title
                  class="clickable"
                  @click="updateTournament(item.tournamentId)"
                >{{ item.tournamentName }}</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>

          <v-menu offset-y v-if="user !== null && user !== undefined">
            <template v-slot:activator="{ on }">
              <v-col cols="4" class="d-flex justify-center align-center">
                <v-btn target="_blank" text v-on="on">
                  <v-icon>mdi-account-circle</v-icon>
                  Hi {{user.sub}}!
                </v-btn>
              </v-col>
            </template>
            <v-list>
              <v-list-item>
                <h4 @click="$router.push('/user-info')" class="clickable">User Info</h4>
              </v-list-item>
              <v-list-item>
                <h4 @click="logout" class="clickable">Logout</h4>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-row>
      </v-row>
    </v-container>
  </v-app-bar>
</template>

<script>
import auth from "@/auth";

export default {
  props: {
    user: Object,
    teams: Array,
    tournaments: Array
  },
  data() {
    return {};
  },

  methods: {
    logout() {
      auth.logout();
      this.$router.push("/login");
      this.$emit("update-user");
    },
    updateTournament(id) {
      this.$router.push("/tournament-page/" + id);
      this.$emit("update-tournament", id);
    },
    updateTeam(id) {
      this.$router.push("/team-page/" + id);
      this.$emit("update-team", id);
    }
  }
};
</script>

<style scoped>
.clickable {
  cursor: pointer;
}
h1 {
  font-size: 2.5vw;
  font-family: "Lucida Console", Courier, monospace;
}
</style>