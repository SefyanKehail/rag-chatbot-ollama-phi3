import {Button, TextField} from "@vaadin/react-components";
import { ChatAiService } from "Frontend/generated/endpoints";
import {SetStateAction, useState} from "react";
import Markdown from "react-markdown";

export default function Chat() {
    const [question, setQuestion] = useState<string>("");
    const [response, setResponse] = useState<string>("");

    async function send() {
        ChatAiService.ragChat(question).then(
            (res) => {
                setResponse(res);
            }
        )
    }

    return (
        <div>
            <h3>Chat Bot</h3>
            <div>
                <TextField style={{width:'40%'}} onChange={(field => setQuestion(field.target.value))}></TextField>
                <Button theme="primary" onClick={send}>Send</Button>
            </div>
            <div>
                <Markdown>{response}</Markdown>
            </div>
        </div>
    )
}