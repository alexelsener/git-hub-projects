<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn flat text v-on="on">
          <v-icon small>mdi-cog</v-icon>Update Score
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Update Match Up Scores</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field color="#03DAC5" v-model="matchup.homeScore" label="Home Team Score" required />
                <v-text-field color="#03DAC5" v-model="matchup.awayScore" label="Away Team Score" required />
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red lighten-2" text @click="dialog=false; resetMatchScores()">Close</v-btn>
          <v-btn color="#03DAC5" text @click="setWinner(); updateMatchupScores()">Update!</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import auth from "@/auth";
export default {
  props: {
    currentMatchup: Object
  },
  data() {
    return {
      matchup: null,
      dialog: false
    };
  },
  methods: {
    resetMatchScores() {
      this.matchup = Object.assign({}, this.currentMatchup);
    },
    updateMatchupScores() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament/scores`, {
        method: "POST",
        headers: {
          Authorization: "Bearer " + auth.getToken(),
          Accept: "application/json",
          "Content-Type": "application/json"
        },
        body: JSON.stringify(this.matchup)
      }).then(response => {
        if (response.ok) {
          this.$emit("update-scores");
          this.dialog = false;
        }
      });
    },
    setWinner() {
      if (this.matchup.homeScore > this.matchup.awayScore) {
        this.matchup.winnerId = this.matchup.homeTeam.teamId;
      } else if (this.matchup.homeScore < this.matchup.awayScore) {
        this.matchup.winnerId = this.matchup.awayTeam.teamId;
      } else {
        this.matchup.winnerId = null;
      }
    }
  },
  created() {
    this.resetMatchScores();
  }
};
</script>

<style>
</style>