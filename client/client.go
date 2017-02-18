package main

import "net"
import "fmt"
import "bufio"
import "os"
import "flag"
import "strconv"

func main() {

  var host = flag.String("host", "localhost", "Host to connect")
  var port = flag.Int("port", 4404, "Port to connect")
  flag.Parse()

  var addr = *host + ":" + strconv.Itoa(*port)
  fmt.Println("Connecting to " + addr)
  conn, _ := net.Dial("tcp", addr)
  fmt.Println("Connected")

  for {
      reader := bufio.NewReader(os.Stdin)
      fmt.Print(addr + " > ")
      text, _ := reader.ReadString('\n')
      if (text == "q\n" || text == "exit\n") {
        break
      }
      fmt.Fprintf(conn, text + "\n")
      message, _ := bufio.NewReader(conn).ReadString('\n')
      fmt.Print(message)
  }
}
