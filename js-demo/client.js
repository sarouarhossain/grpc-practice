const grpc = require("grpc")
const protoLoader = require("@grpc/proto-loader")
const packageDef = protoLoader.loadSync("todo.proto", {})
const grpcObject = grpc.loadPackageDefinition(packageDef)
const todoPackage = grpcObject.todoPackage

const client = new todoPackage.Todo("localhost:5555", grpc.credentials.createInsecure())

const text = process.argv[2]

client.createTodo({
    "text": text+Math.random()
}, (err, res) => {
    console.log("Server response: " + JSON.stringify(res))
})

client.readTodos({}, (err, res) => {
    //console.log("Server response: " + JSON.stringify(res))
    if(!res.items){
       res.items.forEach(item =>console.log(item.text))
    }
})