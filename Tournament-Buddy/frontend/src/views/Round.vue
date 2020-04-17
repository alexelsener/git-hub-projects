<template>
  <v-container>
    <v-row>
      <v-col class="d-flex flex-wrap justify-center" cols="12">
        <v-btn
          :to="{name: 'tournament-page', params: {id: this.tournament.tournamentId}}"
          class="backbutton"
        >🏆🎮🏆 BACK TO TOURNAMENT HOME PAGE 🏆🎮🏆</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col class="d-flex flex-wrap justify-center" cols="12">
        <v-card class="matchup mt-5 at-5" v-for="matchup in matchups" :key="matchup">
          <v-card-title class="justify-center">
            <h3 class="h1 mb-3 font-weight-normal">MATCH</h3>
          </v-card-title>
          <v-card-text>
            <v-card>
              <p v-if="matchup.homeTeam.teamName != null">
                Home Team:
                <span class="hometeam">{{matchup.homeTeam.teamName}}</span>
              </p>
              <p v-if="matchup.homeTeam.teamName == null">
                Home Team:
                <span class="hometeam">BYE</span>
              </p>
              <p v-if="matchup.homeScore > 0">
                Score:
                <span class="hometeam">{{matchup.homeScore}}</span>
              </p>
            </v-card>
            <v-card>
              <p v-if="matchup.awayTeam.teamName != null">
                Away Team:
                <span class="awayteam">{{matchup.awayTeam.teamName}}</span>
              </p>
              <p v-if="matchup.awayTeam.teamName == null">
                Away Team:
                <span class="awayteam">BYE</span>
              </p>
              <p v-if="matchup.homeScore > 0">
                Score:
                <span class="awayteam">{{matchup.awayScore}}</span>
              </p>
            </v-card>
            <v-row>
              <v-col class="d-flex justify-center" cols="12">
                <v-card width="200">
                  <v-spacer></v-spacer>
                  <p v-if="matchup.homeScore > matchup.awayScore">
                    Winner:
                    <span class="hometeam">{{matchup.homeTeam.teamName}}</span>
                  </p>
                  <p v-if="matchup.homeScore < matchup.awayScore">
                    Winner:
                    <span class="awayteam">{{matchup.awayTeam.teamName}}</span>
                  </p>
                  <v-spacer></v-spacer>
                </v-card>
              </v-col>
            </v-row>
          </v-card-text>
          <v-card-actions v-if="tournament.tournamentOwner === currentUser">
            <v-spacer></v-spacer>
            <update-score :current-matchup="matchup" @update-scores="getMatchUps()" />
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import auth from "@/auth";
import UpdateScore from "@/components/UpdateScore.vue";
export default {
  components: {
    UpdateScore
  },
  data() {
    return {
      tournament: null,
      currentUser: null,
      matchups: []
    };
  },
  methods: {
    getMatchUps() {
      let tourneyId = this.$route.params.id;
      let roundNumber = this.$route.params.roundNumber;
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/tournament/matchups?tournamentId=${tourneyId}&round=${roundNumber}`,
        {
          method: "GET",
          headers: {
            Authorization: "Bearer " + auth.getToken()
          }
        }
      )
        .then(response => {
          if (response.ok) {
            return response.json();
          }
        })
        .then(data => {
          this.matchups = data;
        });
    },
    getTournament() {
      const tourneyId = this.$route.params.id;
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament/${tourneyId}`, {
        method: "GET",
        headers: {
          Authorization: "Bearer " + auth.getToken()
        }
      })
        .then(response => {
          if (response.ok) {
            return response.json();
          }
        })
        .then(data => {
          this.tournament = data;
        });
    }
  },
  created() {
    this.getTournament();
    this.currentUser = auth.getUser().id;
    this.getMatchUps();
  }
};
</script>

<style scoped>
.backbutton {
  color: rgb(131, 216, 250);
  font-weight: bolder;
  font-style: unset;
}
.matchup {
  width: 350px;
  height: auto;
  margin-right: 10px;
  margin-left: 10px;
}
.hometeam {
  font-weight: bolder;
  color: rgb(53, 199, 143);
}
.awayteam {
  font-weight: bolder;
  color: rgb(131, 216, 250);
}
* {
  font-family: "Lucida Console", Courier, monospace;
}
p {
  font-size: 12pt;
}
</style>