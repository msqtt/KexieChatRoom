package com.example.websocket.controller;

import com.example.websocket.config.PictureMD5Pool;
import com.example.websocket.config.SessionPool;
import com.example.websocket.config.UserIdPool;
import com.example.websocket.model.OutMessage;
import com.example.websocket.tools.CompressPic;
import com.example.websocket.tools.JSONChange;
import com.example.websocket.tools.TimeFormatter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class UploadController {

    @Value("${file.path.pic}")
    private String picfilePath;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("userId") String userId, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) return "null";

        System.out.println("pic form");
        String fileName = file.getOriginalFilename();
        String bassName = FilenameUtils.getBaseName(fileName);
        String lastIndex = FilenameUtils.getExtension(fileName);

        File tmp = new File(picfilePath + fileName);
        String path = tmp.getParentFile().getPath() + '/';

        if (!tmp.getParentFile().exists()) {
            tmp.getParentFile().mkdirs();
        }

        try {

            byte[] infileBytes = file.getBytes();
            OutMessage out;
            String outJson;
            if (PictureMD5Pool.exists(infileBytes)) {
                String md5 = PictureMD5Pool.getMD5(infileBytes);

                String Name = PictureMD5Pool.picMD5.get(md5);

                BufferedImage image = ImageIO.read(new File(picfilePath + Name));

                out = new OutMessage(TimeFormatter.formatTime(), SessionPool.sessions.size(), userId, 3, "/pic/" + Name, image.getWidth(), image.getHeight());
                outJson = JSONChange.objToJson(out);

                SessionPool.sendMessage(outJson);
                return "success";
            }


            file.transferTo(tmp);

            boolean flag = CompressPic.CompressPic(Files.newInputStream(tmp.toPath()), path, bassName, lastIndex);

            if (flag) lastIndex = "jpg";

            PictureMD5Pool.addMD5(infileBytes, bassName + "." + lastIndex);
            PictureMD5Pool.addMD5(new byte[(int) new File(path + bassName + "." + lastIndex).length()], bassName + "." + lastIndex);

            BufferedImage image = ImageIO.read(new File(path + bassName + "." + lastIndex));

            out = new OutMessage(TimeFormatter.formatTime(),SessionPool.sessions.size(), userId, 3, "/pic/" + bassName + "." + lastIndex, image.getWidth(), image.getHeight());
            outJson = JSONChange.objToJson(out);

            SessionPool.sendMessage(outJson);

            if (flag) tmp.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }


        return "success";
    }

    @Value("${file.path.head}")
    private String headfilePath;
    @RequestMapping("/login")
    public String login(@RequestParam("userId") String id, @RequestPart("img") MultipartFile img, HttpServletResponse res) {
        System.out.println("head form");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Cache-Control", "no-cache");

        if (UserIdPool.User.contains(id)) return "{\"status\":\"false\",\"msg\":\"登录失败，该账户已经被注册过了\"}";

        UserIdPool.User.add(id);


        String headPath =  headfilePath + id + ".jpg";
        File saveDir = new File( headPath);
        if (!saveDir.getParentFile().exists()) {
            saveDir.getParentFile().mkdirs();
        }
        try {
            img.transferTo(saveDir);
            CompressPic.CompressHead(headfilePath, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"status\":\"true\",\"msg\":\"登录成功\"}";
    }

}
