<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn outlined color="#03DAC5" dark v-on="on" @click="checkIfMember()">
          <v-icon dark>mdi-send</v-icon>Join
        </v-btn>
      </template>
      <v-card>
        <v-form v-model="isValid" ref="joinTournamentForm">
          <v-card-title>
            <v-row>
              <v-col cols="6">
                <span v-if="isMember === false" class="headline">Join Tournament</span>
                <span v-if="isMember === true" class="body-1">Your team is in this tournament. </span>
              </v-col>
              <v-col class="d-flex" cols="6" >
                <v-select
                  v-if="isMember === false"
                  :items="userTeams"
                  item-text="teamName"
                  v-model="selectedTeam"
                  label="Team Joining:"
                  @change="getIdFromTeamObj();"
                  color="#03DAC5"
                  dense
                  outlined
                  required
                ></v-select>
              </v-col>
            </v-row>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-textarea
                    color="#03DAC5"
                    outlined
                    label="Message Tournament Host"
                    v-model="request.message"
                    :disabled="isMember === true"
                    required
                  ></v-textarea>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
        </v-form>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="#03DAC5" text @click="dialog = false;">Close</v-btn>
          <v-btn
            color="#03DAC5"
            :disabled="(!isValid) || (isMember === true)"
            text
            @click="dialog = false ; sendJoinRequest()"
          >Join</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import auth from "@/auth";
import api from "@/api.js";

export default {
  name: "JoinTournament",

  props: {
    tournamentId: Number,
    game: String
  },

  data: () => ({
    joinTournamentRules: [
      v => !!v || "Please enter a message. ",
      v =>
        (v && v.length >= 10) ||
        " Your message needs to be 10 characters or more."
    ],
    dialog: false,
    isValid: true,
    request: {
      senderId: "",
      recipientId: "",
      message: ""
    },
    userId: "",
    userTeams: [
      {
        teamId: "",
        teamName: "",
        game: "",
        teamBio: "",
        acceptingNewMembers: ""
      }
    ],
    selectedTeam: "",
    selectedTeamObj: [],
    tourneys: [],
    isMember: false
  }),

  methods: {
    sendJoinRequest() {
      if (this.$refs.joinTournamentForm.validate()) {
        fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament/join-request`, {
          method: "POST",
          headers: {
            Authorization: "Bearer " + auth.getToken(),
            Accept: "application/json",
            "content-type": "application/json"
          },
          body: JSON.stringify(this.request)
        }).then(response => {
          if (response.ok) {
            this.$emit("join-success");
          } else {
            this.$emit("duplicate-request");
          }
        });
      }
    },

    getCaptainsTeamsByGame() {
      fetch(
        `${process.env.VUE_APP_REMOTE_API}/api/team/captain-teams?game=${this.game}&userId=${this.userId}`,
        {
          method: "GET",
          headers: {
            Authorization: "Bearer " + auth.getToken()
          }
        }
      )
        .then(response => {
          return response.json();
        })
        .then(captainsTeams => (this.userTeams = captainsTeams));
    },

    setTournament() {
      this.request.recipientId = this.tournamentId;
    },
    setTeam() {
      this.request.senderId = this.selectedTeamObj.teamId;
    },
    setUserId() {
      this.userId = auth.getUser().id;
    },
    getIdFromTeamObj() {
      this.selectedTeamObj = this.userTeams.find(
        team => team.teamName === this.selectedTeam
      );
      this.setTeam();
    },

    checkIfMember() {
      this.tourneys.forEach(t => {
        if (t.tournamentId === this.request.recipientId) {
          this.isMember = true;
        }
      });
    }
  },

  created() {
    this.setTournament();
    this.setTeam();
    this.setUserId();
    this.getCaptainsTeamsByGame();
    api.getUserTournaments().then(result => (this.tourneys = result));
  },

  computed: {
    getListOfUserGames() {
      return this.userTeams.map(a => a.teamName);
    }
  }
};
</script>



<style>
</style>