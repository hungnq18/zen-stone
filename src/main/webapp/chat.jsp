<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>ZenStone</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; background: #f9f9f9; }
        .chat-box { max-width: 600px; margin: 0 auto; background: white; padding: 1rem; border-radius: 8px; box-shadow: 0 0 8px rgba(0,0,0,.1); }
        .messages { min-height: 200px; padding: 0.5rem; background: #f0f0f0; border-radius: 4px; overflow-y: auto; max-height: 400px; margin-bottom: 1rem; }
        .message { margin-bottom: 0.5rem; padding: 0.5rem; border-radius: 4px; }
        .user { background: #cfe9ff; text-align: right; }
        .bot { background: #e6e6e6; text-align: left; }
        .input-box { display: flex; gap: 0.5rem; }
        .input-box input { flex: 1; padding: 0.5rem; }
        .input-box button { padding: 0.5rem 1rem; cursor: pointer; }
    </style>
</head>
<body>

<div class="chat-box">
    <h2>Chat với AI</h2>
    <div id="messages" class="messages"></div>
    <div class="input-box">
        <input type="text" id="userMessage" placeholder="Nhập tin nhắn..." onkeydown="if(event.key==='Enter'){sendMessage();}">
        <button onclick="sendMessage()">Gửi</button>
    </div>
</div>

<script>
function addMessage(content, sender) {
    var div = document.createElement('div');
    div.className = 'message ' + sender;
    div.innerText = content;
    document.getElementById('messages').appendChild(div);
    div.scrollIntoView();
}

function sendMessage() {
    var msgInput = document.getElementById('userMessage');
    var message = msgInput.value.trim();
    if (message === '') return;
    addMessage(message, 'user');
    msgInput.value = '';
    // Gửi request AJAX
    fetch('chat-bot', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'message=' + encodeURIComponent(message)
    })
    .then(res => res.json())
    .then(data => {
        if (data.reply) {
            addMessage(data.reply, 'bot');
        } else if (data.error) {
            addMessage('Error: ' + data.error, 'bot');
        }
    })
    .catch(err => addMessage('Error: ' + err.message, 'bot'));
}
</script>

</body>
</html>
