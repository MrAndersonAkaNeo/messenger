
const users = {
    v: 'voko',
    k: 'kirpich',
    m: 'masha',
    d: 'danylo'
}

const {v, k, m, d} = users

const ctWS = (senderId, senderName, recipientIDs, recipientNames) => {
    let socket = new SockJS('http://localhost:8080/ws');
    let st = Stomp.over(socket);

    return new Promise((resolve, reject) => {
        st.connect({}, function (msg) {
            console.log(msg)
            document.getElementById('con').innerHTML = msg
            resolve(() => console.log('resolved'))
        })
    })
        .then(() => st.subscribe(`/topic/messages/user/${senderId}`, (msg) => {
            console.log(msg);
        }))
        .then(() => {
            for (let i = 0; i<3; i++){
            sendMessage(st, senderId, recipientIDs[i], senderName, recipientNames[i])
            }
        })
        .then(() => st.unsubscribe(`/topic/messages/user/${senderId}`, (msg) => {
            console.log(msg);
        }))
        .then(() => st.disconnect())
        .catch((error) => console.log(error))
}

const sendMessage = (stompClient, senderId, recipientId, senderName, recipientName) => {
    stompClient.send("/app/chat", {}, JSON.stringify({
        'text': `${senderName} to ${recipientName}`,
        'senderId': senderId,
        'recipientId': recipientId
    }));
}

export {v, k, m, d, ctWS}