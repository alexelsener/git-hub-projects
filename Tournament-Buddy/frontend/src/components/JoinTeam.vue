<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <template v-slot:activator="{ on }">
        <v-btn outlined color="#03DAC5" dark v-on="on" @click="checkIfMember()">
          <v-icon dark>mdi-send</v-icon>Join
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <v-row>
            <span v-if="isMember === false" class="headline">Join Team</span>
            <span v-if="isMember === true" class="headline">You are a member of this team.</span>
          </v-row>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-form ref="joinTeamForm" v-model="isValid">
                  <v-textarea
                    outlined
                    label="Message Team Captian"
                    v-model="request.message"
                    :rules="msgRules"
                    :disabled="isMember == true"
                    required
                    color="#03DAC5"
                  ></v-textarea>
                </v-form>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="#03DAC5" text @click="dialog = false;">Close</v-btn>
          <v-btn
            color="#03DAC5"
            text
            :disabled="(!isValid) || (isMember === true)"
            @click=" sendJoinRequest()"
            @join-success="dialog=false"
            
          >Join</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import auth from "@/auth";

export default {
  name: "JoinTeam",

  props: {
    teamId: Number
  },

  data: () => ({
    dialog: false,
    isValid: true,
    request: {
      senderId: "",
      recipientId: "",
      message: ""
    },
    userTeams: null,
    isMember: false,
    msgRules: [
      v => !!v || "Please enter a message. ",
      v =>
        (v && v.length >= 10) ||
        " Your message needs to be 10 characters or more."
    ]
  }),

  methods: {
    sendJoinRequest() {
      if (this.isMember === false) {
        if (this.$refs.joinTeamForm.validate()) {
          fetch(`${process.env.VUE_APP_REMOTE_API}/api/team/join-request`, {
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
              this.dialog = false;
            } else {
              this.$emit("duplicate-request");
              this.dialog = false;
            }
          });
        }
      }
    },
    setUser() {
      this.request.senderId = auth.getUser().id;
    },
    setTeam() {
      this.request.recipientId = this.teamId;
    },
    checkIfMember() {
      this.userTeams.forEach(team => {
        if (team.teamId === this.request.recipientId) {
          this.isMember = true;
        } 
      });
    }
  },
  created() {
    this.setUser();
    this.setTeam();

    fetch(
      `${process.env.VUE_APP_REMOTE_API}/api/team/?userId=${auth.getUser().id}`,
      {
        method: "GET",
        headers: new Headers({
          Authorization: "Bearer " + auth.getToken()
        }),
        credentials: "same-origin"
      }
    )
      .then(response => {
        if (response.ok) {
          return response.json();
        }
      })
      .then(data => {
        this.userTeams = data;
      })
      .catch(err => console.error(err));
  }
};
</script>



<style>
</style>