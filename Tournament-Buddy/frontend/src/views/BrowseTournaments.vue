<template>
  <v-container>
    <v-card-title large flat>Browse Tournaments</v-card-title>

    <v-container>
      <v-row>
        <v-col cols="1" class="d-flex justify-start align-center">
          <v-card></v-card>
          <p>Filter by:</p>
        </v-col>

        <v-col xs="3" md="3">
          <v-overflow-btn
            color="#03DAC5"
            :items="dropdown_games"
            label="Choose Sport"
            v-model="gamefilter"
            target="#dropdown-example"
          ></v-overflow-btn>
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12" md="4" class="d-flex align-center">
          <v-card outlined class="d-flex justify-center align-center">
            <v-card-text>Show Closed Tournaments?</v-card-text>
            <v-card-actions>
              <v-switch
                color="#03DAC5"
                inset
                v-model="show_closed_tournaments"
                :label="show_closed_tournaments? 'Yes':'No'"
              ></v-switch>
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <v-data-table :headers="headers" :items="sortTournaments" flat>
      <template v-slot:item="row">
        <tr>
          <td v-if="user === null || user === undefined">{{row.item.tournamentName}}</td>
          <td v-if="user !== null" class="clickable" @click="$router.push('/tournament-page/' + row.item.tournamentId)">
            <v-btn small outlined block>{{row.item.tournamentName}}</v-btn>
          </td>
          <td>{{row.item.game}}</td>
          <td>{{row.item.startDate}}</td>
          <td>{{row.item.endDate}}</td>
          <td>{{row.item.prizeDescription}}</td>
          <td>
            <join-tournament
              v-if="row.item.acceptingEntries===true && tournamentStarted(row.item) === false && user !== null"
              :game="row.item.game"
              :tournamentId="row.item.tournamentId"
              @join-success="handleSnack()"
              @duplicate-request="duplicateRequestSnack()"
            ></join-tournament>
            <v-row justify="center">
              <v-btn
                class="mxauto"
                v-if="row.item.acceptingEntries===false || tournamentStarted(row.item) === true || user === null || user === undefined"
                disabled
                color="primary"
              >
                <v-icon dark>mdi-send</v-icon>Join
              </v-btn>
            </v-row>
          </td>
        </tr>
      </template>
    </v-data-table>

    <v-snackbar v-model="snackbar">
      {{ snackText }}
      <v-btn color="#03DAC5" text @click="snackbar = false">Close</v-btn>
    </v-snackbar>
  </v-container>
</template>

<script>
import auth from "@/auth.js";
import JoinTournament from "@/components/JoinTournament.vue";

export default {
  components: {
    JoinTournament
  },
  data() {
    return {
      gamefilter: "All",
      dropdown_games: [
        "All",
        "Volleyball",
        "Basketball",
        "Soccer",
        "Hockey",
        "Bike Polo",
        "Euchre",
        "Poker",
        "DnD",
        "Magic The Gathering",
        "Super Smash Brothers",
        "Other"
      ],

      show_closed_tournaments: true,
      headers: [
        {
          text: "Tournament",
          align: "start",
          sortable: true,
          value: "name"
        },
        { text: "Game", value: "game" },
        { text: "Start Date", value: "startDate" },
        { text: "End Date", value: "endDate" },
        { text: "Tournament Prize", value: "prize" },
        { text: "", value: "join" }
      ],
      tournaments: [
        {
          tournamentName: "",
          game: "",
          tournamentBio: ""
        }
      ],
      captainsTeams: [
        {
          teamName: "",
          teamBio: "",
          game: ""
        }
      ],
      userId: "",
      user: this.getUser(),
      snackbar: false,
      snackText: "Your request has been submitted!"
    };
  },

  computed: {
    sortTournaments() {
      if (this.gamefilter === "All" && this.show_closed_tournaments) {
        return this.tournaments;
      } else if (this.gamefilter !== "All" && this.show_closed_tournaments) {
        return this.tournaments.filter(tourney => tourney.game === this.gamefilter);
      } else if (this.gamefilter === "All" && !this.show_closed_tournaments) {
        return this.tournaments.filter(tourney => tourney.acceptingEntries === true && !this.tournamentStarted(tourney));
      } else {
        return this.tournaments.filter(tourney => {
          return (
            tourney.acceptingEntries === true && tourney.game === this.gamefilter && !this.tournamentStarted(tourney)
          );
        });
      }
    }
  },
  created() {
    fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament/public`, {
      method: "GET",
      headers: new Headers({
        Authorization: "Bearer " + auth.getToken()
      }),
      credentials: "same-origin"
    })
      .then(response => {
        if (response.ok) {
          return response.json();
        }
      })
      .then(data => {
        this.tournaments = data;
      })
      .catch(err => console.error(err));
  },
  methods: {
    handleSnack() {
      this.snackText = "Thank you, your request has been submitted!";
      this.snackbar = true;
    },
    duplicateRequestSnack() {
      this.snackText =
        "You must join with a compatible team, or you already have a pending request";
      this.snackbar = true;
    },
    tournamentStarted(tournament) {
      var date1 = tournament.startDate;
      date1 = new Date(date1);
      var date2 = new Date();
      return date1 <= date2;
    },
    getUser() {
      return auth.getUser();
    }
  }
};
</script>

<style>
.elevation-5 {
  width: 15em;
}
</style>