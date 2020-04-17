<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn color="#03DAC5"
            outlined dark v-on="on">Team Requests</v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="headline">Sign Up Requests</span>
          <v-spacer></v-spacer>
          <v-icon class="clickable" ripple @click="dialog = false">mdi-close-box-outline</v-icon>
        </v-card-title>
        <v-data-table :headers="headers" :items="requests" class="elevation-10">
          <template v-slot:item="row">
            <tr>
              <td>{{row.item.senderName}}</td>
              <td>{{row.item.message}}</td>
              <td>
                <v-row class="justify-end">
                <v-btn
                  class="mx-2"
                  fab
                  dark
                  small
                  color="#03DAC5"
                  @click="dialog = false;acceptRequest(row.item)"
                >
                  <v-icon dark>mdi-check-circle-outline</v-icon>
                </v-btn>
                <v-btn
                  class="mx-2"
                  fab
                  dark
                  small
                  color="red lighten-2"
                  @click="dialog = false;deleteRequest(row.item)"
                >
                  <v-icon dark>mdi-minus-circle-outline</v-icon>
                </v-btn>
                </v-row>
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import auth from "@/auth.js";
export default {
  props: {
    currentTourney: Object
  },
  data() {
    return {
      tourney: null,
      dialog: false,
      requests: [
      ]
    };
  },
  methods: {
    setTournament() {
      this.tourney = Object.assign({}, this.currentTourney);
    },
    getTournamentRequests() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/tournament/request?tournamentId=${this.tourney.tournamentId}`,
        {
          headers: {
            Authorization: "Bearer " + auth.getToken(),
            Accept: "application/json",
            "content-type": "application/json"
          }
        }
      )
        .then(response => {
          if (response.ok) {
            return response.json();
          }
        })
        .then(data => {
          this.requests = data;
        })
        .catch(err => console.error(err));
    },
    deleteRequest(requestToDelete) {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament/request`, {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + auth.getToken(),
          Accept: "application/json",
          "content-type": "application/json"
        },
        body: JSON.stringify(requestToDelete)
      }).then(response => {
        if (response.ok) {
          this.$emit("request-update");
          this.getTournamentRequests();
        }
      });
    },
    acceptRequest(requestToAccept) {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament/request`, {
        method: "POST",
        headers: {
          Authorization: "Bearer " + auth.getToken(),
          Accept: "application/json",
          "content-type": "application/json"
        },
        body: JSON.stringify(requestToAccept)
      }).then(response => {
        if (response.ok) {
          this.$emit("request-update");
          this.deleteRequest(requestToAccept);
        }
      });
    }
  },
  created() {
    this.setTournament();
    this.getTournamentRequests();
  }
};
</script>

<style>
</style>