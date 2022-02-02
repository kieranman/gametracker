// const url = 'http://localhost:8080';
// let stompClient;
// let selectedUser;
// function connection(username){
//     console.log("connecting...")
//     let socket = new SockJS(url + '/chat');
//     stompClient = Stomp.over(socket); //means you use stomp client over websockets
//     stompClient.connect({},function(frame)
//     {
//         console.log("connect to: "+ frame);
//         stompClient.subscribe("/topic/messages/"+username,function(response){
//             let data = JSON.parse(response.body);
//             console.log(data);
//         })
//     });

// }

// function sendMessage(from,text){
//     stompClient.send("/app/chat/"+ selectedUser,{},JSON.stringify(
//         {fromLogin:from,
//          message:text
        
//         }))
// }