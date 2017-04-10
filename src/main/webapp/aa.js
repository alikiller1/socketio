var talk = null;  
var senderId=$("#teacherId").val();  
var receiverId=$("#selected-student-id").val();  
var senderName=$("#teacherName").val();  
var receiverName=$("#selected-student-name").val();  
var taskId=$("#task-id").val();  
//以上都是用来获取当前人的id，和你要发给谁的人的id.  
  
var socket;  
//断开socket连接  
function sendDisconnect() {  
   socket.disconnect();  
}  
  
//消息中心 提醒  
function appendChatContent(data) {  
        var content=data.content;  
        talk.append({text:content});  
}  
  
function setupIO() {  
    socket =  io.connect('http://localhost:10000/'+senderId);  
  
    socket.on('connect', function() {  
        console.log('server connected.');  
    });  
      
    socket.on('taskchatevent', function(data) {  
    //监听当前跟谁聊天，避免，多个学生发向同一个窗口  
        if(data.sendUserId==receiverId){  
            appendChatContent(data);  
        }else{  
        //找学生列表的学生id，如果找到对应的，就一闪一闪。。。。  
        }  
    });  
      
    socket.on('disconnect', function() {  
        //TODO  
    });  
}  
//发送消息给对方  
function emitContent(text){  
// socket.disconnect();  
socket=io.connect('http://localhost:10000/'+receiverId);  
  
socket.emit("taskchatevent",{receiveUserId:receiverId,sendUserId:senderId,content:text});  
  
/* socket.disconnect();不可以重新监听自己的信息 再使用下面的，相当于又建立一个连接。。。（所以隐了起来，放在这是提示自己不能这么做的原因） 
 setupIO();*/  
}  
$(function() {  
    setupIO();  
    /*下面这段的用到了一个插件，这个插件是自己写的，大概意思就是将内容 name神马的，显示到前端的一个框框中，自己可以做个简单的，或者不要直接写到一个div中也一样的。*/  
    talk = $('#tmessage').mdtalk({  
          me: {  
            _id: senderId,  
            name: senderName,  
            header: 'http://img.woyaogexing.com/2015/01/16/bccaafc6a51d0e47!200x200.png'  
          },  
          to: {  
            _id: receiverId,  
            name: receiverName,  
            header: 'http://img.woyaogexing.com/2015/01/15/93078204e588ef99!200x200.jpg'  
          },  
    //前端点击发送时会触发这个事件，因为我把聊天记录存储到了db中，所以还用到了ajax.  
          send: function(text) {  
            var date = new Date();  
            console.log(date.toLocaleDateString() + ' me: ' + text);  
            var promise = new Promise();  
              
            $.ajax({  
                url : addTaskTalkUrl,  
                data: {taskId:taskId,content:text,receiverId:receiverId},  
                type : 'POST',  
                dataType : 'json',  
                success:function(result){  
                    //插入成功  
                    if(result && result.taskTalkVo){  
                        promise.resolve();  
                    } else {  
                        promise.reject();  
                    }  
                      
                    //把内容 发送给对方监听   
                    emitContent(text);  
                },  
                error: function() {  
                    promise.reject();  
                }  
            });  
            return promise;  
          }  
        });  
          
      });  