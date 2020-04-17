<template>
  <v-container>
    <v-row>
      <v-col class="d-flex flex-wrap justify-center" cols="12">
        <v-btn
          :to="{name: 'tournament-page', params: {id: this.tournament.tournamentId}}"
          class="backbutton"
        ><v-icon color="#DCAE3F">mdi-trophy-outline</v-icon>
         <v-icon color="#A4A3A2">mdi-google-controller</v-icon>
         <v-icon color="#DCAE3F">mdi-trophy-outline</v-icon> 
         BACK TO TOURNAMENT HOME PAGE 
         <v-icon color="#DCAE3F">mdi-trophy-outline</v-icon>
         <v-icon color="#A4A3A2">mdi-google-controller</v-icon>
         <v-icon color="#DCAE3F">mdi-trophy-outline</v-icon> 
         </v-btn>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col cols="6" sm="3">
        <v-img :src="require('../assets/logo.svg')" class="my-3" contain height="150" />
      </v-col>
      <v-col cols="6" sm="3">
        <h1>{{tournament.tournamentName}}</h1>
      </v-col>
      <v-col cols="12" sm="3">
        <h3>Tournament Game/Sport:</h3>
        <p>{{tournament.game}}</p>
      </v-col>
      <v-col cols="12" sm="3">
        <h3>Tournament Prize Description:</h3>
        <p>{{tournament.prizeDescription}}</p>
      </v-col>
    </v-row>

    <p
      v-if="homeTeam === awayTeam && homeTeam !== null && selectedMatchup !== 'BYE'"
      class="red--text text-center"
    >Can't choose same team for Home and Away</p>

    <v-row justify="center">
      <v-col class="d-flex" cols="6" sm="3">
        <v-select
          :disabled="teams.length === 0"
          :items="matchups"
          label="Match"
          v-model="selectedMatchup"
          outlined
          item-text
          color="#03DAC5"
        ></v-select>
      </v-col>
      <v-col class="d-flex" cols="6" sm="3">
        <v-select
          :items="teams"
          label="Home Team"
          item-text="teamName"
          return-object="true"
          v-model="homeTeam"
          outlined
          :disabled="teams.length === 0"
          color="#03DAC5"
        ></v-select>
      </v-col>
      <v-col class="d-flex" cols="6" sm="3">
        <v-select
          :items="teams"
          label="Away Team"
          item-text="teamName"
          return-object="true"
          v-model="awayTeam"
          outlined
          :disabled="selectedMatchup === 'BYE' || teams.length === 0"
          color="#03DAC5"
        ></v-select>
      </v-col>
      <v-col class="d-flex" cols="6" sm="2">
        <v-btn
          v-if="readyToSubmit && teams.length > 0"
          @click="createMatchup()"
          color="#03DAC5"
        >Add Matchup</v-btn>
        <v-btn v-if="readyToSubmit === false && teams.length > 0" disabled>Add Match</v-btn>
        <v-btn v-if="teams.length === 0" color="success" @click="submitMatchups()">Submit Matches</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col class="justify-center d-flex">
        <v-btn
          v-if="finalMatchups.length > 0"
          color="error"
          small
          @click="resetMatchups()"
        >Reset Matches</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col v-for="matchup in finalMatchups" :key="matchup" col="12" sm="3">
        <v-card class="mx-auto" outlined v-if="matchup.awayTeam !== null">
          <v-list-item-content class="text-center">
            <v-list-item-title class="headline mb-1">{{matchup.homeTeam.teamName}}</v-list-item-title>
            <v-list-item-subtitle>Vs.</v-list-item-subtitle>
            <v-list-item-title class="headline mb-1">{{matchup.awayTeam.teamName}}</v-list-item-title>
          </v-list-item-content>
        </v-card>
        <v-card class="mx-auto" outlined v-if="matchup.awayTeam === null">
          <v-list-item-content class="text-center">
            <v-list-item-title class="headline mb-1">{{matchup.homeTeam.teamName}}</v-list-item-title>
            <v-list-item-subtitle>has a</v-list-item-subtitle>
            <v-list-item-title class="headline mb-1">BYE</v-list-item-title>
          </v-list-item-content>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import api from "@/api.js";
import auth from "@/auth.js";

export default {
  data() {
    return {
      tournament: null,
      teams: null,
      matchups: null,
      homeTeam: null,
      awayTeam: null,
      selectedMatchup: null,
      round: Number.parseInt(this.$route.params.round),
      finalMatchups: []
    };
  },
  computed: {
    readyToSubmit() {
      if (this.selectedMatchup !== "BYE") {
        return (
          this.homeTeam !== null &&
          this.awayTeam !== null &&
          this.selectedMatchup !== null &&
          this.homeTeam !== this.awayTeam
        );
      } else {
        return this.homeTeam !== null;
      }
    }
  },
  methods: {
    getTournament() {
      api
        .getTournament(this.$route.params.id)
        .then(results => (this.tournament = results));
    },
    getTournamentTeams() {
      api.getTourneyTeams(this.$route.params.id).then(results => {
        this.teams = results;
        this.numOfMatchups(results);
      });
    },
    resetMatchups(){
      this.finalMatchups = [];
      this.matchups = [];
      this.getTournamentTeams();
    },
    numOfMatchups(teams) {
      let numOfMatchups = Math.ceil(teams.length / 2);
      let emptyMatchups = [];

      if (this.teams.length % 2 === 0) {
        for (let index = 0; index < numOfMatchups; index++) {
          emptyMatchups.push("Match " + (index + 1));
        }
      } else {
        for (let index = 1; index < numOfMatchups; index++) {
          emptyMatchups.push("Match " + index);
        }
        emptyMatchups.push("BYE");
      }

      this.matchups = emptyMatchups;
    },
    createMatchup() {
      if (this.selectedMatchup === "BYE") {
        this.finalMatchups.push({
          round: this.round,
          tournamentId: this.tournament.tournamentId,
          homeTeam: this.homeTeam,
          awayTeam: null
        });
      } else {
        this.finalMatchups.push({
          round: this.round,
          tournamentId: this.tournament.tournamentId,
          homeTeam: this.homeTeam,
          awayTeam: this.awayTeam
        });
      }
      this.teams = this.teams.filter(
        team => team !== this.homeTeam && team !== this.awayTeam
      );
      this.matchups = this.matchups.filter(
        matchup => matchup !== this.selectedMatchup
      );

      this.homeTeam = null;
      this.awayTeam = null;
      this.selectedMatchup = null;
    },
    submitMatchups() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament/matchups`, {
        method: "POST",
        headers: {
          Authorization: "Bearer " + auth.getToken(),
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.finalMatchups)
      }).then(response => {
        if (response.ok) {
          this.$router.push(`/tournament-page/${this.tournament.tournamentId}`);
        }
      });
    }
  },
  created() {
    this.getTournament();
    this.getTournamentTeams();
  }
};
</script>

<style>
</style>