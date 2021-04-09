const grpc = require("grpc")
const protoLoader = require("@grpc/proto-loader")
const packageDef = protoLoader.loadSync("todo.proto", {})
const grpcObject = grpc.loadPackageDefinition(packageDef)
const todoPackage = grpcObject.todoPackage

const client = new todoPackage.Todo("localhost:5555", grpc.credentials.createInsecure())

const call = client.readTodosStream()
call.on("data", item => {
    console.log("received data: "+JSON.stringify(item))
})

call.on("end", e=> console.log("server done!"))