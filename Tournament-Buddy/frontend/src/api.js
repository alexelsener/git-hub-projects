import auth from '@/auth.js'

export default {

      getUserTeams(){
        return fetch(`${process.env.VUE_APP_REMOTE_API}/api/team?userId=${auth.getUser().id}`,  {
        method: 'GET',
        headers: {
          Authorization: 'Bearer ' + auth.getToken(),
        },
        credentials: 'same-origin',
      })
      .then((response) => {
          return response.json();
        })
      .then(teamsFromApi => {
        return teamsFromApi;
      })
      },


       getUserTournaments(){
        return fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament?userId=${auth.getUser().id}`,  {
        method: 'GET',
        headers: {
          Authorization: 'Bearer ' + auth.getToken(),
        },
        credentials: 'same-origin',
      })
      .then((response) => {
          return response.json();
        })
      .then(tourneysFromApi => {
        return tourneysFromApi;
      })
      },


      getCaptainedTeams(){
        return fetch(`${process.env.VUE_APP_REMOTE_API}/api/user/captain?id=${auth.getUser().id}`,  {
        method: 'GET',
        headers: {
          Authorization: 'Bearer ' + auth.getToken(),
        },
        credentials: 'same-origin',
      })
      .then((response) => {
          return response.json();
        })
      .then(apiCaptainedTeams => {
        return apiCaptainedTeams;
      })
      },


      getUser(){
        let username = auth.getUser().sub;
      return fetch(`${process.env.VUE_APP_REMOTE_API}/api/user/${username}`,  {
        method: 'GET',
        headers: {
          Authorization: 'Bearer ' + auth.getToken(),
        },
      })
      .then((response) => {
          return response.json();
        })
      .then(userFromApi => {
        return userFromApi;
        })
      },


      getTournament(tourneyId) {
        return fetch(`${process.env.VUE_APP_REMOTE_API}/api/tournament/${tourneyId}`, {
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
            return data;
          });
      },


      getTourneyTeams(tourneyId) {
       return fetch(
          `${process.env.VUE_APP_REMOTE_API}/api/team/tournament/${tourneyId}`,
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
          .then(teamsInfo => {
            return teamsInfo;
          });
      },

      getTeam(teamId){
        return fetch(`${process.env.VUE_APP_REMOTE_API}/api/team/${teamId}`, {
          method: "GET",
          headers: {
            Authorization: "Bearer " + auth.getToken()
          }
        })
          .then(response => {
            return response.json();
          })
          .then(data => {
            return data;
          });
      }
}