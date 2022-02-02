import React, { Component } from 'react';

import AuthService from '../services/AuthService';
import {Button} from 'react-bootstrap';
import '../chat/ChatBox.css';

const user = AuthService.getCurrentUser();
var stompClient = null;

class Chatroom extends Component {
  constructor(props) {
    super(props);
    
    this.state = {
      username: '',
      channelConnected: false,
      chatMessage: '',
      roomNotification: [],
      broadcastMessage: [],
      error: '',
      bottom: false,
      curTime: '',
      openNotifications: false,
      bellRing: false,
      currentUser:undefined,
      messages:[]

    };

    
  }

  connect = () => {

    if (user) {
  
      const Stomp = require('stompjs')
  
      var SockJS = require('sockjs-client')
  
      SockJS = new SockJS('http://localhost:8080/ws')
  
      stompClient = Stomp.over(SockJS);
  
      stompClient.connect({}, this.onConnected, this.onError);
      
  
  
      this.setState({
        username: user.username,
      })
    }
  }
  
  onConnected = () => {

    this.setState({
      channelConnected: true
    })

    // Subscribing to the public topic
    stompClient.subscribe('/topic/pubic', this.onMessageReceived);
    // Registering user to server as a public chat user
    stompClient.send("/app/addUser", {}, JSON.stringify({ sender: this.state.username, type: 'JOIN' }))

  }

  sendMessage = (type, value) => {

    if (stompClient) {
      var chatMessage = {
        sender: this.state.username,
        content: type === 'TYPING' ? value : value,
        type: type

      };
      // send public message
      stompClient.send("/app/sendMessage", {}, JSON.stringify(chatMessage));

    }
  }

  onMessageReceived = (payload) => {

    var message = JSON.parse(payload.body);

    if (message.type === 'JOIN') {

      this.state.roomNotification.push({ 'sender': message.sender + " ~ joined", 'status': 'online', 'dateTime': message.dateTime })
      this.setState({
        roomNotification: this.state.roomNotification,
        bellRing: true
      })

    }
    else if (message.type === 'LEAVE') {
      this.state.roomNotification.map((notification, i) => {
        if (notification.sender === message.sender + " ~ joined") {
          notification.status = "offline";
          notification.sender = message.sender + " ~ left";
          notification.dateTime = message.dateTime;
        }
      })
      this.setState({
        roomNotification: this.state.roomNotification,
        bellRing: true
      })
    }
    else if (message.type === 'TYPING') {

      this.state.roomNotification.map((notification, i) => {
        if (notification.sender === message.sender + " ~ joined") {
          if (message.content)
            notification.status = "typing...";
          else
            notification.status = "online";
        }

      })
      this.setState({
        roomNotification: this.state.roomNotification
      })
    }
    else if (message.type === 'CHAT') {

      this.state.roomNotification.map((notification, i) => {
        if (notification.sender === message.sender + " ~ joined") {
          notification.status = "online";
        }
      })
      this.state.broadcastMessage.push({
        message: message.content,
        sender: message.sender,
        dateTime: message.dateTime
      })
      this.setState({
        broadcastMessage: this.state.broadcastMessage,

      })
    }
    else {
      // do nothing...
    }
  }

  onError = (error) => {
    this.setState({
      error: 'Could not connect you to the Chat Room Server. Please refresh this page and try again!'
    })
  }



  handleSendMessage = () => {
    
    this.sendMessage('CHAT', this.state.chatMessage)

        this.setState({
          chatMessage: ''
        })

}

handleTyping = (event) => {

  this.setState({
      chatMessage: event.target.value,
  });
  this.sendMessage('TYPING', event.target.value);

};

 componentWillMount() {



  this.setState({
    curTime: new Date().toLocaleString()
  })




  if (user) {
    this.setState({
      currentUser: user,
    });
  }
  this.connect()
 }
 



 render() {

  return (
    
      <div className="chat-container">
      <div className="past-chat">
        <ul>
        {this.state.broadcastMessage.map((msg, i) =>

        
        this.state.username === msg.sender ?
        // if the message is from user
        
        <li className ="you">
          <div className="you-contents">
            <img src={"https://archives.bulbagarden.net/media/upload/4/43/FireRed_EN_boxart.png"} class="avatar"/>
            <div className="you-message">{msg.message}</div>
          </div>
          
          
            
        </li>:
        // if message is from other participant
        <li className ="other">
          <div className="other-contents">
            <img src={"https://archives.bulbagarden.net/media/upload/4/43/FireRed_EN_boxart.png"} class="avatar"/>
            <div className="other-message">{msg.message}</div>
          </div>
        </li>
        )}
      </ul>
        </div>
        <div>
          <input type="text" value={this.state.chatMessage} onChange={this.handleTyping}></input>
          <Button className="mt-auto font-weight-bold"
                                variant ="secondary" onClick={this.handleSendMessage}>Send</Button>
        </div>


      </div>


  );
}
}

export default Chatroom;
