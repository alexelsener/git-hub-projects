export default{
    getImage(id){
        if(id % 12 === 0){
            return require("@/assets/avatars/avatar-1.png");
        } else if(id % 11 === 0){
            return require("@/assets/avatars/avatar-2.png");
        } else if(id % 10 === 0){
            return require("@/assets/avatars/avatar-3.png");
        } else if(id % 9 === 0){
            return require("@/assets/avatars/avatar-4.png");
        } else if(id % 8 === 0){
            return require("@/assets/avatars/avatar-5.png");
        } else if(id % 7 === 0){
            return require("@/assets/avatars/avatar-6.png");
        } else if(id % 6 === 0){
            return require("@/assets/avatars/avatar-7.png");
        } else if(id % 5 === 0){
            return require("@/assets/avatars/avatar-8.png");
        } else if(id % 4 === 0){
            return require("@/assets/avatars/avatar-9.png");
        } else if(id % 3 === 0){
            return require("@/assets/avatars/avatar-10.png");
        } else if(id % 2 === 0){
            return require("@/assets/avatars/avatar-11.png");
        } else{ 
            return require("@/assets/avatars/avatar-12.png");
        } 
    }


}