-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
                           `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
                           `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                           `sex` char(6) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                           `pwd` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                           `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'test0', '男', 'aaaa', '1230@qq.com');
INSERT INTO `t_user` VALUES (2, 'test1', '女', 'aaaa', '1231@qq.com');
INSERT INTO `t_user` VALUES (3, 'test2', '男', 'aaaa', '1232@qq.com');
INSERT INTO `t_user` VALUES (4, 'test3', '女', 'aaaa', '1233@qq.com');
INSERT INTO `t_user` VALUES (5, 'test4', '男', 'aaaa', '1234@qq.com');
INSERT INTO `t_user` VALUES (6, 'test5', '女', 'aaaa', '1235@qq.com');
INSERT INTO `t_user` VALUES (7, 'test6', '男', 'aaaa', '1236@qq.com');
INSERT INTO `t_user` VALUES (8, 'test7', '女', 'aaaa', '1237@qq.com');
INSERT INTO `t_user` VALUES (9, 'test8', '男', 'aaaa', '1238@qq.com');
INSERT INTO `t_user` VALUES (10, 'test9', '女', 'aaaa', '1239@qq.com');
INSERT INTO `t_user` VALUES (11, 'test10', '男', 'aaaa', '12310@qq.com');
INSERT INTO `t_user` VALUES (12, 'test11', '女', 'aaaa', '12311@qq.com');
INSERT INTO `t_user` VALUES (13, 'test12', '男', 'aaaa', '12312@qq.com');
INSERT INTO `t_user` VALUES (14, 'test13', '女', 'aaaa', '12313@qq.com');
INSERT INTO `t_user` VALUES (15, 'test14', '男', 'aaaa', '12314@qq.com');