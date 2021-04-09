const grpc = require("grpc")
const protoLoader = require("@grpc/proto-loader")
const packageDef = protoLoader.loadSync("todo.proto", {})
const grpcObject = grpc.loadPackageDefinition(packageDef)
const todoPackage = grpcObject.todoPackage

const server = new grpc.Server()
server.bind("0.0.0.0:5555", grpc.ServerCredentials.createInsecure())

server.addService(todoPackage.Todo.service, {
    "createTodo": createTodo,
    "readTodos": readTodos,
    "readTodosStream": readTodosStream
})

server.start();

const todos = []
let id = 100
function createTodo(call, callback) {
    let todo = {
        "id": id++,
        "text": call.request.text
    }

    todos.push(todo)

    console.log("Current State: " + JSON.stringify(todos))
    callback(null, todo)
}

function readTodos(call, callback) {
    callback(null, {
        "items": todos
    })
}

function readTodosStream(call, callback) {
    todos.forEach(function (item, index) {
        call.write(item)
    })
    call.end();
}

