<template>
  <v-app>
    <nav-bar
      :user="currentUser"
      :teams="currentTeams"
      :tournaments="currentTournaments"
      @update-user="currentUser = getUser(); getUserTeams(); getUserTournaments()"
      @update-tournament="getTournament($event)"
      @update-team="getTeam($event)"
    />
    <v-content>
      <router-view
        :currentTeam="team"
        :currentTournament="tournament"
        @update-user="currentUser = getUser(); getUserTeams(); getUserTournaments()"
        @update-tournament="getTournament($event)"
        @update-team="getTeam($event)"
      />
    </v-content>
  </v-app>
</template>

<script>
import NavBar from "./components/NavBar.vue";
import auth from "@/auth";
import api from "@/api.js";

export default {
  name: "App",
  components: {
    NavBar
  },
  data() {
    return {
      currentUser: this.getUser(),
      currentTeams: this.getUserTeams(),
      currentTournaments: this.getUserTournaments(),
      team: null,
      tournament: null
    };
  },
  methods: {
    getUser() {
      return auth.getUser();
    },
    getUserTeams() {
      if (auth.getUser() === null || auth.getUser() === undefined) {
        this.currentTeams = [];
      } else {
        api.getUserTeams().then(results => (this.currentTeams = results));
      }
    },
    getUserTournaments() {
      if (auth.getUser() === null || auth.getUser() === undefined) {
        this.currentTournaments = [];
      } else {
        api
          .getUserTournaments()
          .then(tourneysFromApi => (this.currentTournaments = tourneysFromApi));
      }
    },
    getTournament(id) {
      api.getTournament(id).then(tourney => (this.tournament = tourney));
    },
    getTeam(id) {
      api.getTeam(id).then(data => (this.team = data));
    }
  }
};
</script>
<style>
.clickable {
  cursor: pointer;
}
* {
  font-family: "Lucida Console", Courier, monospace;
}
</style>
