package cn.com.gwssi.patent.xml.service.impl;

import cn.com.gwssi.patent.xml.config.XmlPathConfig;
import cn.com.gwssi.patent.xml.model.*;
import cn.com.gwssi.patent.xml.service.IPatentCodedFileLoad;
import cn.com.gwssi.patent.xml.service.IPatentXmlAnalysis;
import cn.com.gwssi.patent.xml.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class PatentXmlAnalysisImp implements IPatentXmlAnalysis {

    @Autowired
    private XmlPathConfig xmlPathConfig;
    @Autowired
    private IPatentCodedFileLoad patentCodedFileLoad;

    @Override
    public PatentFile_100001 claim(@NotEmpty String shenqingh, @NotEmpty Integer version) {
        log.info("进入权利要求书解析处理逻辑");
        Integer fileCode = FileInfoConstants.FILE_CODE_CLAIM;
        PatentFile_100001 patentFile100001 = (PatentFile_100001) this.commonPatentFileAnalysis(shenqingh, fileCode, version);
        if (patentFile100001 == null) {
            return null;
        }

        if (patentFile100001.getCnClaims() == null) {
            return patentFile100001;
        }

        if (patentFile100001.getCnClaims().getP() != null) {
            List<P> pList = patentFile100001.getCnClaims().getP();
            if (pList != null && !pList.isEmpty()) {
                for (cn.com.gwssi.patent.xml.model.P p : pList) {
                    if (p.getText() == null || p.getText().indexOf("<img") == -1) {
                        continue;
                    }
                    p.setText(formatImgInText(p.getText(), xmlPathConfig.getFileLinkUrl()));
                }
            }
        }

        if (patentFile100001.getCnClaims().getClaimList() != null) {
            List<Claim> claims = patentFile100001.getCnClaims().getClaimList();
            if (claims != null && !claims.isEmpty()) {
                for (Claim claim : claims) {
                    List<ClaimText> claimTexts = claim.getClaimTextList();
                    for (ClaimText claimText : claimTexts) {
                        if (claimText.getText() == null || claimText.getText().indexOf("<img") == -1) {
                            continue;
                        }
                        claimText.setText(formatImgInText(claimText.getText(), xmlPathConfig.getFileLinkUrl()));
                    }
                }
            }
        }
        return patentFile100001;
    }

    @Override
    public PatentFile_100002 description(@NotEmpty String shenqingh, @NotEmpty Integer version) {
        log.info("进入说明书解析处理逻辑");
        Integer fileCode = FileInfoConstants.FILE_CODE_DESCRIPTION;
        PatentFile_100002 patentFile100002 = (PatentFile_100002) this.commonPatentFileAnalysis(shenqingh, fileCode, version);
        if (patentFile100002 == null) {
            return null;
        }
        Description des = patentFile100002.getDescription();
        BackgroundArt_100002 backgroundArt = des.getBackgroundArt();
        DescriptionOfDrawings_100002 descriptionOfDrawings = des.getDescriptionOfDrawings();
        Disclosure_100002 disclosure = des.getDisclosure();
        ModeForInvention_100002 modeForInvention = des.getModeForInvention();
        TechnicalField_100002 technicalField = des.getTechnicalField();
        List<P> pList = des.getPList();
        formatImgInP(pList, xmlPathConfig.getFileLinkUrl());

        if (pList != null) {
            formatImgInP(pList, xmlPathConfig.getFileLinkUrl());
        }
        if (backgroundArt != null && backgroundArt.getPList() != null) {
            formatImgInP(backgroundArt.getPList(), xmlPathConfig.getFileLinkUrl());
        }
        if (descriptionOfDrawings != null && descriptionOfDrawings.getPList() != null) {
            formatImgInP(descriptionOfDrawings.getPList(), xmlPathConfig.getFileLinkUrl());
        }
        if (disclosure != null && disclosure.getPList() != null) {
            formatImgInP(disclosure.getPList(), xmlPathConfig.getFileLinkUrl());
        }
        if (modeForInvention != null && modeForInvention.getpList() != null) {
            formatImgInP(modeForInvention.getpList(), xmlPathConfig.getFileLinkUrl());
        }
        if (technicalField != null && technicalField.getpList() != null) {
            formatImgInP(technicalField.getpList(), xmlPathConfig.getFileLinkUrl());
        }
        return patentFile100002;
    }

    @Override
    public PatentFile_100002 description(@NotEmpty String shenqingh,@NotEmpty String fileEntieyPath) {
        log.info("进入说明书解析处理逻辑");
        Integer fileCode = FileInfoConstants.FILE_CODE_DESCRIPTION;
        PatentFile_100002 patentFile100002 = (PatentFile_100002) this.commonPatentFileAnalysis(shenqingh,fileEntieyPath, fileCode);
        if (patentFile100002 == null) {
            return null;
        }
        Description des = patentFile100002.getDescription();
        BackgroundArt_100002 backgroundArt = des.getBackgroundArt();
        DescriptionOfDrawings_100002 descriptionOfDrawings = des.getDescriptionOfDrawings();
        Disclosure_100002 disclosure = des.getDisclosure();
        ModeForInvention_100002 modeForInvention = des.getModeForInvention();
        TechnicalField_100002 technicalField = des.getTechnicalField();
        List<P> pList = des.getPList();
        formatImgInP(pList, xmlPathConfig.getFileLinkUrl());

        if (pList != null) {
            formatImgInP(pList, xmlPathConfig.getFileLinkUrl());
        }
        if (backgroundArt != null && backgroundArt.getPList() != null) {
            formatImgInP(backgroundArt.getPList(), xmlPathConfig.getFileLinkUrl());
        }
        if (descriptionOfDrawings != null && descriptionOfDrawings.getPList() != null) {
            formatImgInP(descriptionOfDrawings.getPList(), xmlPathConfig.getFileLinkUrl());
        }
        if (disclosure != null && disclosure.getPList() != null) {
            formatImgInP(disclosure.getPList(), xmlPathConfig.getFileLinkUrl());
        }
        if (modeForInvention != null && modeForInvention.getpList() != null) {
            formatImgInP(modeForInvention.getpList(), xmlPathConfig.getFileLinkUrl());
        }
        if (technicalField != null && technicalField.getpList() != null) {
            formatImgInP(technicalField.getpList(), xmlPathConfig.getFileLinkUrl());
        }
        return patentFile100002;
    }

    @Override
    public PatentFile_100003 descriptionDrawing(@NotEmpty String shenqingh, @NotEmpty Integer version) {
        log.info("进入说明书附图解析处理逻辑");
        Integer fileCode = FileInfoConstants.FILE_CODE_DESCRIPTION_DRAWING;
        PatentFile_100003 patentFile100003 = (PatentFile_100003) this.commonPatentFileAnalysis(shenqingh, fileCode, version);
        if (patentFile100003 == null) {
            return null;
        }
        DescriptionDrawing drawings = patentFile100003.getDescriptionDrawing();
        if (drawings == null) {
            return null;
        }
        List<Figure> figures = drawings.getFigureList();
        if (figures == null || figures.isEmpty()) {
            return null;
        }
        figures.parallelStream().forEach(f -> {
            Img img = f.getImg();
            if (img != null) {
                String imgUrl = xmlPathConfig.getFileLinkUrl() + patentFile100003.getBaseDir().replace(xmlPathConfig.getBasePath(), "")+ FileHelperUtil.FILE_PATH_SEPARATOR+
                        img.getFileName().replace(".tif", ".jpg").replace(".tiff", ".jpg");
                img.setImgUrl(imgUrl);
            }
        });
        return patentFile100003;
    }

    @Override
    public PatentFile_100004 descriptionAbstract(@NotEmpty String shenqingh, @NotEmpty Integer version) {
        log.info("进入说明书摘要解析处理逻辑");
        Integer fileCode = FileInfoConstants.FILE_CODE_DESCRIPTION_ABSTRACT;
        PatentFile_100004 patentFile100004 = (PatentFile_100004)this.commonPatentFileAnalysis(shenqingh, fileCode, version);

        if (patentFile100004 == null) {
            return null;
        }

        if (patentFile100004.getDescriptionAbstract() == null) {
            return patentFile100004;
        }

        List<P> pList = patentFile100004.getDescriptionAbstract().getpList();
        if (pList != null && !pList.isEmpty()) {
            for (cn.com.gwssi.patent.xml.model.P p : pList) {
                if (p.getText() == null || p.getText().indexOf("<img") == -1) {
                    continue;
                }
                p.setText(formatImgInText(p.getText(),  xmlPathConfig.getFileLinkUrl()));
            }
        }

        return patentFile100004;
    }

    @Override
    public PatentFile_100005 abstractDrawing(@NotEmpty String shenqingh, @NotEmpty Integer version) {
        log.info("进入摘要附图解析处理逻辑");
        Integer fileCode = FileInfoConstants.FILE_CODE_ABSTRACT_DRAWING;
        PatentFile_100005 patentFile100005 = (PatentFile_100005)this.commonPatentFileAnalysis(shenqingh, fileCode, version);

        if (patentFile100005 == null) {
            return null;
        }
        AbstractDrawing cnAbstract = patentFile100005.getAbstractDrawing();
        if (cnAbstract == null) {
            return null;
        }
        CnAbstFigure cnAbstFigure = cnAbstract.getCnAbstFigure();
        if (cnAbstFigure == null) {
            return null;
        }
        List<Figure> figures = cnAbstFigure.getFigures();
        if (figures == null || figures.isEmpty()) {
            return null;
        }
        figures.parallelStream().forEach(f -> {
            Img img = f.getImg();
            if (img != null) {
                String imgUrl = xmlPathConfig.getFileLinkUrl() + patentFile100005.getBaseDir().replace(xmlPathConfig.getBasePath(), "")+ FileHelperUtil.FILE_PATH_SEPARATOR+
                        img.getFileName().replace(".tif", ".jpg").replace(".tiff", ".jpg");
                img.setImgUrl(imgUrl);
            }
        });
        return patentFile100005;
    }

    private Object commonPatentFileAnalysis(String shenqingh,String fileEntieyPath, Integer fileCode) {
        String version = "1";
        CodedFile codedFile = new CodedFile();
        codedFile.setShenqingh(shenqingh);
        codedFile.setWenjianlxdm("" + fileCode);
        codedFile.setBanbenh("1");
        codedFile.setFileEntityPath(fileEntieyPath);

        Path path = Paths.get(fileEntieyPath);
        Path dir = path.getParent();

        String filePath = patentCodedFileLoad.fileEntityLoad(codedFile, dir);

        if (StringUtils.isBlank(filePath)) {
            throw new BusinessException("目录[" + dir.toString() + "]下不存在xml文件", BizCodeContants.FILE_NOT_EXISTS);
        }
        String context = FileHelperUtil.readContentFromFile(filePath, "UTF-8");
        context = PathentXmlUtil.transferTemplet(context, shenqingh, fileCode);
        if (fileCode.equals(FileInfoConstants.FILE_CODE_DESCRIPTION)) {//对说明书进行修整
            context = this.tagPSelfCloseProcess(context);
            context = this.tagHeadingPostionProcess(context);
        }
        //将<p>标签里的其它标签转义
        Matcher matcher = Pattern.compile("<p[^>]*>(((?!(</p>)).)*)</p>").matcher(context);
        while (matcher.find()) {
            //获取p标签的内容(带p标签)
            String pStr = matcher.group();
            //获取p标签的内容(不带p标签)
            String pContent = matcher.group(1);
            //将p标签里其它标签转义
            String zhuanyihou = pContent.replaceAll("<!\\-\\-((?!(\\-\\->)).)*\\-\\->", "").replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            String pStrAfter = pStr.replace(pContent, zhuanyihou);
            context = context.replace(pStr, pStrAfter);
        }


        //将<claim-text>标签里的其它标签转义
        Matcher claimmatcher = Pattern.compile("<claim\\-text[^>]*>(((?!(</claim\\-text>)).)*)</claim\\-text>").matcher(context);
        while (claimmatcher.find()) {
            //获取claim-text标签的内容(带claim-text标签)
            String pStr = claimmatcher.group();
            //获取claim-text标签的内容(不带claim-text标签)
            String pContent = claimmatcher.group(1);
            //将claim-text标签里其它标签转义
            String zhuanyihou = pContent.replaceAll("<!\\-\\-((?!(\\-\\->)).)*\\-\\->", "").replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            String pStrAfter = pStr.replace(pContent, zhuanyihou);
            context = context.replace(pStr, pStrAfter);
        }

        if (fileCode.equals(FileInfoConstants.FILE_CODE_CLAIM)) {//对于权利要求进行格式修整
            context = FileEntityUtil.getClaimsText(context);
        }

        String clazzPath = "cn.com.gwssi.patent.xml.model.PatentFile_" + fileCode;
        //String clazzPath = "cn.com.gwssi.patent.xml.model.ClaimText";
        Class<?> clazz = null;
        Object clazzObj = null;
        try {
            clazz = Class.forName(clazzPath);
            clazzObj = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new BusinessException("类文件未找到[" + clazzPath + "]", BizCodeContants.CLASS_NOT_FIND_EXCEPTION);
        } catch (IllegalAccessException | InstantiationException e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new BusinessException("调用[" + clazzPath + ".newInstance()]异常", BizCodeContants.CLASS_INSTANTIATION_EXCEPTION);
        }
        Method method = ReflectionUtils.findMethod(clazz, "parseXmlText", String.class, String.class, String.class, String.class);
        //Method method = ReflectionUtils.findMethod(clazz, "getText");
        if (method == null) {
            throw new BusinessException("类方法不存在[" + clazzPath + "." + "parseXmlText(String,String,String)]", BizCodeContants.METHOD_NOT_FIND_EXCEPTION);
        }
        Object obj = ReflectionUtils.invokeMethod(method, clazzObj, new String[]{context, shenqingh, "" + version, dir.toString()});
        return obj;
    }

    private Object commonPatentFileAnalysis(String shenqingh, Integer fileCode, Integer version) {
        //获取最新的文件路径
        CodedFile codedFile = patentCodedFileLoad.fileAttribueLoad(shenqingh, fileCode, version);
        if (codedFile == null || StringUtils.isBlank(codedFile.getFid()) || StringUtils.isBlank(codedFile.getDmh_zskwjb_id())) {
            //throw new BusinessException("申请号:"+shenqingh+",版本号:"+version+",代码化文件不存在", BizCodeContants.FILE_NOT_EXISTS);
        }

        String filePath = FileHelperUtil.findCodedFile(xmlPathConfig, shenqingh, fileCode, version, codedFile.getZuihouxgsj());
        Path path = Paths.get(filePath);
        Path dir = path.getParent();
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                log.error(ExceptionUtil.getMessage(e));
                throw new BusinessException("目录[" + filePath + "]创建异常", BizCodeContants.DIR_CREATE_EXCEPTION);
            }
        }
        if (!Files.exists(path)) {
            filePath = patentCodedFileLoad.fileEntityLoad(codedFile, dir);
        }
        if (StringUtils.isBlank(filePath)) {
            throw new BusinessException("目录[" + dir.toString() + "]下不存在xml文件", BizCodeContants.FILE_NOT_EXISTS);
        }
        String context = FileHelperUtil.readContentFromFile(filePath, "UTF-8");
        context = PathentXmlUtil.transferTemplet(context, shenqingh, fileCode);
        if (fileCode.equals(FileInfoConstants.FILE_CODE_DESCRIPTION)) {//对说明书进行修整
            context = this.tagPSelfCloseProcess(context);
            context = this.tagHeadingPostionProcess(context);
        }
        //将<p>标签里的其它标签转义
        Matcher matcher = Pattern.compile("<p[^>]*>(((?!(</p>)).)*)</p>").matcher(context);
        while (matcher.find()) {
            //获取p标签的内容(带p标签)
            String pStr = matcher.group();
            //获取p标签的内容(不带p标签)
            String pContent = matcher.group(1);
            //将p标签里其它标签转义
            String zhuanyihou = pContent.replaceAll("<!\\-\\-((?!(\\-\\->)).)*\\-\\->", "").replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            String pStrAfter = pStr.replace(pContent, zhuanyihou);
            context = context.replace(pStr, pStrAfter);
        }


        //将<claim-text>标签里的其它标签转义
        Matcher claimmatcher = Pattern.compile("<claim\\-text[^>]*>(((?!(</claim\\-text>)).)*)</claim\\-text>").matcher(context);
        while (claimmatcher.find()) {
            //获取claim-text标签的内容(带claim-text标签)
            String pStr = claimmatcher.group();
            //获取claim-text标签的内容(不带claim-text标签)
            String pContent = claimmatcher.group(1);
            //将claim-text标签里其它标签转义
            String zhuanyihou = pContent.replaceAll("<!\\-\\-((?!(\\-\\->)).)*\\-\\->", "").replaceAll("<", "&lt;").replaceAll(">", "&gt;");

            String pStrAfter = pStr.replace(pContent, zhuanyihou);
            context = context.replace(pStr, pStrAfter);
        }

        if (fileCode.equals(FileInfoConstants.FILE_CODE_CLAIM)) {//对于权利要求进行格式修整
            context = FileEntityUtil.getClaimsText(context);
        }

        String clazzPath = "cn.com.gwssi.patent.xml.model.PatentFile_" + fileCode;
        //String clazzPath = "cn.com.gwssi.patent.xml.model.ClaimText";
        Class<?> clazz = null;
        Object clazzObj = null;
        try {
            clazz = Class.forName(clazzPath);
            clazzObj = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new BusinessException("类文件未找到[" + clazzPath + "]", BizCodeContants.CLASS_NOT_FIND_EXCEPTION);
        } catch (IllegalAccessException | InstantiationException e) {
            log.error(ExceptionUtil.getMessage(e));
            throw new BusinessException("调用[" + clazzPath + ".newInstance()]异常", BizCodeContants.CLASS_INSTANTIATION_EXCEPTION);
        }
        Method method = ReflectionUtils.findMethod(clazz, "parseXmlText", String.class, String.class, String.class, String.class);
        //Method method = ReflectionUtils.findMethod(clazz, "getText");
        if (method == null) {
            throw new BusinessException("类方法不存在[" + clazzPath + "." + "parseXmlText(String,String,String)]", BizCodeContants.METHOD_NOT_FIND_EXCEPTION);
        }
        Object obj = ReflectionUtils.invokeMethod(method, clazzObj, new String[]{context, shenqingh, "" + version, dir.toString()});
        return obj;
    }

    /**
     * xtream不支持自闭合标签,现对<p Italic="0" num="0011"/>此种格式处理成<p Italic="0" num="0011"></p>
     *
     * @param xmlText
     */
    private String tagPSelfCloseProcess(String xmlText) {
        StringBuilder strBui = new StringBuilder();
        List<RegexMatch> matchFilterList = new ArrayList<>();
        //(<p )(.*?)(\/>) 匹配<p空格*>格式
        List<RegexMatch> matchList = PatternUtil.findMatch(xmlText, "(<p )(.*?)(>)");//匹配<p空格*/>格式
        matchList.stream().filter(rm -> PatternUtil.isMatch(rm.getMatchStr(), "(<p )(.*?)(\\/>)")).
                forEach(rm -> {
                    rm.setMatchStr(rm.getMatchStr().replace("/>", "></p>"));
                    matchFilterList.add(rm);
                });
        if (matchFilterList.size() == 0) {
            return xmlText;
        }
        int size = matchFilterList.size();
        int loop = 1;
        for (RegexMatch regexMatch : matchFilterList) {
            int start = regexMatch.getStart();
            int end = regexMatch.getEnd();

            if (loop == 1 && start > 0) {
                strBui.append(xmlText.substring(0, start));
            }
            strBui.append(regexMatch.getMatchStr());
            if (loop == size && end < xmlText.length()) {
                strBui.append(xmlText.substring(end));
            }
            loop++;
        }
        return strBui.toString();
    }

    /**
     * <heading>*</heading>在某些类型中存在多个
     *
     * @param xmlText
     */
    private String tagHeadingPostionProcess(String xmlText) {
        StringBuilder strBui = new StringBuilder();
        Set<String> existsStrSet = new HashSet<>();
        List<RegexMatch> matchFilterList = new ArrayList<>();
        //(<p )(.*?)(\/>) 匹配<p空格*>格式
        List<RegexMatch> matchList = PatternUtil.findMatch(xmlText, "(<heading>)(.*?)(</heading>)");//匹配<p空格*/>格式
        matchList.stream().forEach(rm -> {
            String headingText = rm.getMatchStr().replace("<heading>", "").replace("</heading>", "");
            String headingType = CaseInfoUtil.PATENT_DESCRIPTION_HEADING_CONTENT.get(headingText);
            if (headingType == null || existsStrSet.contains(headingType)) {
                rm.setMatchStr(rm.getMatchStr().replace("</heading>", "</p>").replace("<heading>", "<p num=\"XXXX\" Italic=\"0\">"));
                matchFilterList.add(rm);
            } else {
                existsStrSet.add(headingType);
            }
        });
        if (matchFilterList.size() == 0) {
            return xmlText;
        }
        int size = matchFilterList.size();
        int loop = 1;
        for (RegexMatch regexMatch : matchFilterList) {
            int start = regexMatch.getStart();
            int end = regexMatch.getEnd();

            if (loop == 1 && start > 0) {
                strBui.append(xmlText.substring(0, start));
            }
            strBui.append(regexMatch.getMatchStr());
            if (loop == size && end < xmlText.length()) {
                strBui.append(xmlText.substring(end));
            }
            loop++;
        }
        return strBui.toString();
    }

    // 获取带有img的p节点下的内容绝对路径
    private static String formatImgInText(String content, String linkFolderPath) {

        String regex = "<img((?!/>|!>).)+(/>|>)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            String imgStr = matcher.group(0);
            String fileName = imgStr.substring(imgStr.indexOf("file=\"") + "file=\"".length(),
                    imgStr.indexOf("\"", imgStr.indexOf("file=\"") + "file=\"".length()));
            String fileNameLast = fileName.replaceFirst(".tif|.TIF|.tiff|.TIFF", ".jpg");
            String srcValue = linkFolderPath + FileHelperUtil.FILE_PATH_SEPARATOR + fileNameLast;
            String imgStrNew = imgStr.replace("file=", "class=\"wushu_img\" src=").replace(fileName, srcValue);
            content = content.replace(imgStr, imgStrNew);
        }
        return content;
    }

    private void formatImgInP(List<P> pList, String linkFolderPath) {
        if (pList != null && !pList.isEmpty()) {
            for (P p : pList) {
                if (p.getText() == null || p.getText().indexOf("<img") == -1) {
                    continue;
                }
                p.setText(formatImgInText(p.getText(), linkFolderPath));
            }
        }
    }
}
